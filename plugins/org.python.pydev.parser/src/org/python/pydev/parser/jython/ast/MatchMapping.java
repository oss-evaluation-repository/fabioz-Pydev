// Autogenerated AST node
package org.python.pydev.parser.jython.ast;

import org.python.pydev.parser.jython.SimpleNode;
import java.util.Arrays;

public final class MatchMapping extends patternType {
    public exprType[] keys;
    public patternType[] values;

    public MatchMapping(exprType[] keys, patternType[] values) {
        this.keys = keys;
        this.values = values;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(keys);
        result = prime * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MatchMapping other = (MatchMapping) obj;
        if (!Arrays.equals(keys, other.keys)) return false;
        if (!Arrays.equals(values, other.values)) return false;
        return true;
    }
    @Override
    public MatchMapping createCopy() {
        return createCopy(true);
    }
    @Override
    public MatchMapping createCopy(boolean copyComments) {
        exprType[] new0;
        if(this.keys != null){
        new0 = new exprType[this.keys.length];
        for(int i=0;i<this.keys.length;i++){
            new0[i] = (exprType) (this.keys[i] != null? this.keys[i].createCopy(copyComments):null);
        }
        }else{
            new0 = this.keys;
        }
        patternType[] new1;
        if(this.values != null){
        new1 = new patternType[this.values.length];
        for(int i=0;i<this.values.length;i++){
            new1[i] = (patternType) (this.values[i] != null?
            this.values[i].createCopy(copyComments):null);
        }
        }else{
            new1 = this.values;
        }
        MatchMapping temp = new MatchMapping(new0, new1);
        temp.beginLine = this.beginLine;
        temp.beginColumn = this.beginColumn;
        if(this.specialsBefore != null && copyComments){
            for(Object o:this.specialsBefore){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsBefore().add(commentType.createCopy(copyComments));
                }
            }
        }
        if(this.specialsAfter != null && copyComments){
            for(Object o:this.specialsAfter){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsAfter().add(commentType.createCopy(copyComments));
                }
            }
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("MatchMapping[");
        sb.append("keys=");
        sb.append(dumpThis(this.keys));
        sb.append(", ");
        sb.append("values=");
        sb.append(dumpThis(this.values));
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitMatchMapping(this);
    }

    @Override
    public void traverse(VisitorIF visitor) throws Exception {
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] != null) {
                    keys[i].accept(visitor);
                }
            }
        }
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    values[i].accept(visitor);
                }
            }
        }
    }

}
