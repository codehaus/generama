package org.generama.astunit.parser;

import antlr.CommonAST;
import antlr.Token;

/**
 * AST that remembers the line and column numbers.
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class TokenKeepingAST extends CommonAST {
    private Token token;

    public TokenKeepingAST() {
        super();
    }

    public void initialize(Token token) {
        super.initialize(token);
        this.token = token;
    }

    public int getLine() {
        return token != null ? token.getLine() : -1;
    }

    public int getColumn() {
        return token != null ? token.getColumn() : -1;
    }

    public boolean equals(Object o) {
        if(!(o instanceof TokenKeepingAST)) {
            return false;
        }
        // Did the ANTLR guys really think equals was polymorphic in Java?? Hmmm.
        return super.equals((TokenKeepingAST)o);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getText());
        String fileName = token != null ? token.getFilename() : "[UNKOWN FILE]";
        sb.append(" (" + fileName + ":" + getLine() + ":" + getColumn() + ")");
        return sb.toString();
    }
}
