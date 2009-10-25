// Autogenerated AST node
package org.python.pydev.parser.jython.ast;
import org.python.pydev.parser.jython.SimpleNode;

public final class With extends stmtType {
    public exprType context_expr;
    public exprType optional_vars;
    public suiteType body;

    public With(exprType context_expr, exprType optional_vars, suiteType body) {
        this.context_expr = context_expr;
        this.optional_vars = optional_vars;
        this.body = body;
    }

    public With(exprType context_expr, exprType optional_vars, suiteType body, SimpleNode parent) {
        this(context_expr, optional_vars, body);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public With createCopy() {
        With temp = new With(context_expr!=null?(exprType)context_expr.createCopy():null,
        optional_vars!=null?(exprType)optional_vars.createCopy():null,
        body!=null?(suiteType)body.createCopy():null);
        temp.beginLine = this.beginLine;
        temp.beginColumn = this.beginColumn;
        if(this.specialsBefore != null){
            for(Object o:this.specialsBefore){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsBefore().add(commentType.createCopy());
                }
            }
        }
        if(this.specialsAfter != null){
            for(Object o:this.specialsAfter){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsAfter().add(commentType.createCopy());
                }
            }
        }
        return temp;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("With[");
        sb.append("context_expr=");
        sb.append(dumpThis(this.context_expr));
        sb.append(", ");
        sb.append("optional_vars=");
        sb.append(dumpThis(this.optional_vars));
        sb.append(", ");
        sb.append("body=");
        sb.append(dumpThis(this.body));
        sb.append("]");
        return sb.toString();
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitWith(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
        if (context_expr != null){
            context_expr.accept(visitor);
        }
        if (optional_vars != null){
            optional_vars.accept(visitor);
        }
        if (body != null){
            body.accept(visitor);
        }
    }

}
