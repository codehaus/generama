package org.generama.astunit.parser;

import antlr.Token;

import java.io.Reader;
import java.io.InputStream;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JavaLexerEx extends JavaLexer {
    public JavaLexerEx(InputStream in) {
        super(in);
        setTokenObjectClass(UncommonToken.class.getName());
    }

    protected Token makeToken(int t) {
        Token token = super.makeToken(t);
        token.setFilename(getFilename());
        return token;
    }
}
