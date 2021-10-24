package org.python.pydev.ast.codecompletion;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.python.pydev.core.IPythonPartitions;
import org.python.pydev.core.IToken;
import org.python.pydev.core.MisconfigurationException;
import org.python.pydev.core.PythonNatureWithoutProjectException;
import org.python.pydev.core.TokensOrProposalsList;
import org.python.pydev.core.docutils.ParsingUtils;
import org.python.pydev.core.docutils.SyntaxErrorException;
import org.python.pydev.core.log.Log;
import org.python.pydev.core.partition.PyPartitionScanner;
import org.python.pydev.shared_core.partitioner.FastPartitioner;
import org.python.pydev.shared_core.string.FastStringBuffer;
import org.python.pydev.shared_core.structure.Tuple;

public class PyCodeCompletionsForTypedDict {

    public static TokensOrProposalsList getStringCompletions(CompletionRequest request) throws CoreException,
            BadLocationException, IOException, MisconfigurationException, PythonNatureWithoutProjectException {
        Optional<CompletionRequest> artificialRequest = createArtificialRequest(request);
        if (artificialRequest.isPresent()) {
            return new PyCodeCompletion().getCodeCompletionProposals(artificialRequest.get());
        }
        return null;
    }

    private static Optional<CompletionRequest> createArtificialRequest(CompletionRequest request)
            throws BadLocationException {
        Optional<Tuple<Optional<String>, Integer>> maybeQualifierAndOffset = getDictKeyQualifierAndOffset(request);
        if (maybeQualifierAndOffset.isPresent()) {
            Tuple<Optional<String>, Integer> qualifierAndOffset = maybeQualifierAndOffset.get();
            Tuple<String, IDocument> parsedQualifierAndDoc = createParsedQualifierAndDoc(qualifierAndOffset, request);
            Optional<String> activationToken = getActivationTokenForDictKey(request, qualifierAndOffset.o2);
            if (activationToken.isPresent()) {
                String parsedQualifier = parsedQualifierAndDoc.o1;
                IDocument parsedDoc = parsedQualifierAndDoc.o2;
                CompletionRequest artificialRequest = new CompletionRequest(
                        request.editorFile, request.nature, parsedDoc, activationToken.get(),
                        request.documentOffset, request.qlen, request.codeCompletion, parsedQualifier,
                        request.useSubstringMatchInCodeCompletion);
                artificialRequest.filterToken = tokenType -> tokenType == IToken.TYPE_ATTR;
                return Optional.of(artificialRequest);
            }
        }
        return Optional.empty();
    }

    private static Optional<Tuple<Optional<String>, Integer>> getDictKeyQualifierAndOffset(CompletionRequest request)
            throws BadLocationException {
        IDocumentPartitioner partitioner = PyPartitionScanner.checkPartitionScanner(request.doc);
        FastPartitioner fastPartitioner = (FastPartitioner) partitioner;
        final ITypedRegion partition = fastPartitioner.getPartition(request.documentOffset);
        if (IPythonPartitions.PY_DEFAULT.equals(partition.getType())) {
            return getDictKeyQualifierAndOffsetForOpenContents(request);
        } else if (IPythonPartitions.PY_SINGLELINE_BYTES_OR_UNICODE1.equals(partition.getType())
                || IPythonPartitions.PY_SINGLELINE_BYTES_OR_UNICODE2.equals(partition.getType())) {
            return getDictKeyQualifierAndOffsetForProbablyCorrectContents(request, partition);
        }
        return Optional.empty();
    }

    private static Optional<Tuple<Optional<String>, Integer>> getDictKeyQualifierAndOffsetForOpenContents(
            CompletionRequest request) throws BadLocationException {
        final IDocument doc = request.doc;
        final FastPartitioner fastPartitioner = ((FastPartitioner) PyPartitionScanner.checkPartitionScanner(doc));
        for (int docOffset = request.documentOffset - 1; docOffset > 0; docOffset--) {
            char c = doc.getChar(docOffset);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == '\'' || c == '"') {
                ITypedRegion p = fastPartitioner.getPartition(docOffset);
                if (docOffset == p.getOffset()) { // the string start and end is at the same offset, meaning that we have an open string declaration.
                    return Optional.of(new Tuple<Optional<String>, Integer>(Optional.empty(), docOffset));
                }
            }
            break;
        }
        return Optional.empty();
    }

    private static Optional<Tuple<Optional<String>, Integer>> getDictKeyQualifierAndOffsetForProbablyCorrectContents(
            CompletionRequest request, final ITypedRegion partition) throws BadLocationException {
        final IDocument doc = request.doc;
        int qualifierOffset = partition.getOffset();
        int strContentLen = partition.getLength() - 2; // we have to ignore both of string identifiers (i.e.: `'` or `"`).
        Optional<String> qualifier = Optional.empty();
        if (strContentLen > 0) {
            // here we have a proper content for qualifier (open and closed string).
            qualifier = Optional.of(doc.get(qualifierOffset + 1, strContentLen)); // extract only qualifier content (without string opener and closer).
        }
        Tuple<Optional<String>, Integer> ret = new Tuple<Optional<String>, Integer>(qualifier, qualifierOffset);
        return Optional.of(ret);
    }

    private static Tuple<String, IDocument> createParsedQualifierAndDoc(
            Tuple<Optional<String>, Integer> qualifierAndOffset, CompletionRequest request)
            throws BadLocationException {
        Optional<String> qualifier = qualifierAndOffset.o1;
        if (qualifier.isEmpty()) {
            IDocument parsedDoc = createParsedDocForOpenQualifier(request.doc, qualifierAndOffset.o2);
            return new Tuple<String, IDocument>("", parsedDoc);
        }
        return new Tuple<String, IDocument>(qualifier.get(), request.doc);
    }

    private static IDocument createParsedDocForOpenQualifier(IDocument doc, int qualifierOffset)
            throws BadLocationException {
        FastStringBuffer buf = new FastStringBuffer(doc.get(), 2);
        int qualifierEndOffset = getQualifierEndOffset(buf.toString(), qualifierOffset);
        if (!isClosingBracketAfterOffset(buf, qualifierEndOffset + 1)) {
            char strOpenerChar = doc.getChar(qualifierOffset);
            int insideStrOffset = qualifierOffset + 1;
            buf.replace(insideStrOffset, insideStrOffset, strOpenerChar + "]");
        }
        return new Document(buf.toString());
    }

    private static int getQualifierEndOffset(String docContent, int qualifierOffset) {
        ParsingUtils parser = ParsingUtils.create(docContent);
        try {
            return parser.eatLiterals(null, qualifierOffset);
        } catch (SyntaxErrorException e) {
            Log.log(e);
        }
        return qualifierOffset;
    }

    private static boolean isClosingBracketAfterOffset(FastStringBuffer buf, int offset) {
        for (int i = offset; i < buf.length(); i++) {
            char c = buf.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            return c == ']';
        }
        return false;
    }

    private static Optional<String> getActivationTokenForDictKey(CompletionRequest request, int qualifierOffset)
            throws BadLocationException {
        final IDocument doc = request.doc;
        for (int i = qualifierOffset - 1; i > 0; i--) {
            char c = doc.getChar(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == '[') {
                String activationToken = extractBeforeBracketActivationToken(i, doc);
                return Optional.of(activationToken);
            }
            break;
        }
        return Optional.empty();
    }

    private static String extractBeforeBracketActivationToken(int keyOffset, final IDocument doc)
            throws BadLocationException {
        int line = doc.getLineOfOffset(keyOffset);
        int lineOffset = doc.getLineOffset(line);
        int activationTokenOffset = keyOffset - 1;
        while (activationTokenOffset > lineOffset) {
            char c = doc.getChar(activationTokenOffset);
            if (!Character.isJavaIdentifierPart(c) && !Character.isWhitespace(c) && c != '.') {
                break;
            }
            activationTokenOffset--;
        }
        String rawActivationToken = doc.get(activationTokenOffset, keyOffset - activationTokenOffset);
        String ret = rawActivationToken.trim();
        if (!ret.endsWith(".")) {
            ret += '.';
        }
        return ret;
    }
}
