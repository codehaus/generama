package org.generama;

import org.xml.sax.SAXException;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class GeneramaException extends RuntimeException {
    public GeneramaException(String message, Throwable t) {
        super(message, t);
    }
}
