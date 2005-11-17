/*
 * Created on 20/08/2005
 */
package org.python.pydev.plugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.osgi.service.prefs.Preferences;
import org.python.pydev.builder.PyDevBuilderPrefPage;
import org.python.pydev.builder.todo.PyTodoPrefPage;

public class PydevPrefsInitializer  extends AbstractPreferenceInitializer{


    @Override
    public void initializeDefaultPreferences() {
        Preferences node = new DefaultScope().getNode(PydevPlugin.DEFAULT_PYDEV_SCOPE);

        //text
        node.putBoolean(PydevPrefs.SMART_INDENT_PAR, PydevPrefs.DEFAULT_SMART_INDENT_PAR);
        node.putBoolean(PydevPrefs.AUTO_PAR, PydevPrefs.DEFAULT_AUTO_PAR);
        node.putBoolean(PydevPrefs.AUTO_COLON, PydevPrefs.DEFAULT_AUTO_COLON);
        node.putBoolean(PydevPrefs.AUTO_BRACES, PydevPrefs.DEFAULT_AUTO_BRACES);
        node.putBoolean(PydevPrefs.AUTO_WRITE_IMPORT_STR, PydevPrefs.DEFAULT_AUTO_WRITE_IMPORT_STR);
    
        node.putInt(PydevPrefs.TAB_WIDTH, PydevPrefs.DEFAULT_TAB_WIDTH);
        node.put(PydevPrefs.MULTI_BLOCK_COMMENT_CHAR, PydevPrefs.DEFAULT_MULTI_BLOCK_COMMENT_CHAR);
        node.put(PydevPrefs.SINGLE_BLOCK_COMMENT_CHAR, PydevPrefs.DEFAULT_SINGLE_BLOCK_COMMENT_CHAR);
        
        //checkboxes
        node.putBoolean(PydevPrefs.SUBSTITUTE_TABS, PydevPrefs.DEFAULT_SUBSTITUTE_TABS);
        node.putBoolean(PydevPrefs.AUTO_ADD_SELF, PydevPrefs.DEFAULT_AUTO_ADD_SELF);
        node.putBoolean(PydevPrefs.GUESS_TAB_SUBSTITUTION, PydevPrefs.DEFAULT_GUESS_TAB_SUBSTITUTION);
        node.putBoolean(PydevPrefs.USE_CODE_FOLDING, PydevPrefs.DEFAULT_USE_CODE_FOLDING);
        node.put(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_USE_CUSTOM_CARETS, StringConverter.asString(PydevPrefs.DEFAULT_EDITOR_USE_CUSTOM_CARETS));
        node.put(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_WIDE_CARET, StringConverter.asString(PydevPrefs.DEFAULT_EDITOR_WIDE_CARET));
        
        //matching
        node.putBoolean(PydevPrefs.USE_MATCHING_BRACKETS, PydevPrefs.DEFAULT_USE_MATCHING_BRACKETS);
        node.put(PydevPrefs.MATCHING_BRACKETS_COLOR, StringConverter.asString(PydevPrefs.DEFAULT_MATCHING_BRACKETS_COLOR));
        node.putInt(PydevPrefs.MATCHING_BRACKETS_STYLE, PydevPrefs.DEFAULT_MATCHING_BRACKETS_STYLE);
        
        //colors
        node.put(PydevPrefs.CODE_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_CODE_COLOR));
        node.put(PydevPrefs.NUMBER_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_NUMBER_COLOR));
        node.put(PydevPrefs.DECORATOR_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_DECORATOR_COLOR));
        node.put(PydevPrefs.KEYWORD_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_KEYWORD_COLOR));
        node.put(PydevPrefs.SELF_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_SELF_COLOR));
        node.put(PydevPrefs.STRING_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_STRING_COLOR));
        node.put(PydevPrefs.COMMENT_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_COMMENT_COLOR));
        node.put(PydevPrefs.BACKQUOTES_COLOR,StringConverter.asString(PydevPrefs.DEFAULT_BACKQUOTES_COLOR));
        //for selection colors see initializeDefaultColors()
        
        //font style
        node.putInt(PydevPrefs.CODE_STYLE, PydevPrefs.DEFAULT_CODE_STYLE);
        node.putInt(PydevPrefs.NUMBER_STYLE, PydevPrefs.DEFAULT_NUMBER_STYLE);
        node.putInt(PydevPrefs.DECORATOR_STYLE, PydevPrefs.DEFAULT_DECORATOR_STYLE);
        node.putInt(PydevPrefs.KEYWORD_STYLE, PydevPrefs.DEFAULT_KEYWORD_STYLE);
        node.putInt(PydevPrefs.SELF_STYLE, PydevPrefs.DEFAULT_SELF_STYLE);
        node.putInt(PydevPrefs.STRING_STYLE, PydevPrefs.DEFAULT_STRING_STYLE);
        node.putInt(PydevPrefs.COMMENT_STYLE, PydevPrefs.DEFAULT_COMMENT_STYLE);
        node.putInt(PydevPrefs.BACKQUOTES_STYLE, PydevPrefs.DEFAULT_BACKQUOTES_STYLE);
        
        //no UI
        node.putInt(PydevPrefs.CONNECT_TIMEOUT, PydevPrefs.DEFAULT_CONNECT_TIMEOUT);
        
        
        //pydev todo tasks
        node.put(PyTodoPrefPage.PY_TODO_TAGS, PyTodoPrefPage.DEFAULT_PY_TODO_TAGS);
        
        //builders
        node.putBoolean(PyDevBuilderPrefPage.USE_PYDEV_BUILDERS, PyDevBuilderPrefPage.DEFAULT_USE_PYDEV_BUILDERS);
        node.putBoolean(PyDevBuilderPrefPage.USE_PYDEV_ANALYSIS_ONLY_ON_DOC_SAVE, PyDevBuilderPrefPage.DEFAULT_USE_PYDEV_ONLY_ON_DOC_SAVE);
        node.putInt(PyDevBuilderPrefPage.PYDEV_ELAPSE_BEFORE_ANALYSIS, PyDevBuilderPrefPage.DEFAULT_PYDEV_ELAPSE_BEFORE_ANALYSIS);
        
        //code formatting
        node.putBoolean(PyCodeFormatterPage.USE_SPACE_AFTER_COMMA,    PyCodeFormatterPage.DEFAULT_USE_SPACE_AFTER_COMMA);
        node.putBoolean(PyCodeFormatterPage.USE_SPACE_FOR_PARENTESIS, PyCodeFormatterPage.DEFAULT_USE_SPACE_FOR_PARENTESIS);

        
    }

}
