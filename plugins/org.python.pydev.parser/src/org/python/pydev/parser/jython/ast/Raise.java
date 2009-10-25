// Autogenerated AST node
package org.python.pydev.parser.jython.ast;
import org.python.pydev.parser.jython.SimpleNode;

public final class Raise extends stmtType {
    public exprType type;
    public exprType inst;
    public exprType tback;
    public exprType cause;

    public Raise(exprType type, exprType inst, exprType tback, exprType cause) {
        this.type = type;
        this.inst = inst;
        this.tback = tback;
        this.cause = cause;
    }

    public Raise(exprType type, exprType inst, exprType tback, exprType cause, SimpleNode parent) {
        this(type, inst, tback, cause);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public Raise createCopy() {
        Raise temp = new Raise(type!=null?(exprType)type.createCopy():null,
        inst!=null?(exprType)inst.createCopy():null, tback!=null?(exprType)tback.createCopy():null,
        cause!=null?(exprType)cause.createCopy():null);
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
        StringBuffer sb = new StringBuffer("Raise[");
        sb.append("type=");
        sb.append(dumpThis(this.type));
        sb.append(", ");
        sb.append("inst=");
        sb.append(dumpThis(this.inst));
        sb.append(", ");
        sb.append("tback=");
        sb.append(dumpThis(this.tback));
        sb.append(", ");
        sb.append("cause=");
        sb.append(dumpThis(this.cause));
        sb.append("]");
        return sb.toString();
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitRaise(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
        if (type != null){
            type.accept(visitor);
        }
        if (inst != null){
            inst.accept(visitor);
        }
        if (tback != null){
            tback.accept(visitor);
        }
        if (cause != null){
            cause.accept(visitor);
        }
    }

}
