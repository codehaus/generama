package org.generama.astunit.parser;

import antlr.CommonToken;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class UncommonToken extends CommonToken {
    private String fileName;

    public UncommonToken() {
        super();
    }

    public UncommonToken(int i, String s) {
        super(i, s);
    }

    public UncommonToken(String s) {
        super(s);
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String fileName) {
        this.fileName = fileName;
    }
}
