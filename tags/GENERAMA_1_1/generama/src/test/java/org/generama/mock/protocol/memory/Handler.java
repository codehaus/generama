package org.generama.mock.protocol.memory;

import java.net.URLStreamHandler;
import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * This handler is used to mock URLs from memory.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class Handler extends URLStreamHandler {
    protected URLConnection openConnection(final URL u) throws IOException {
        return new URLConnection(u) {
            public void connect() throws IOException {

            }

            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream( u.getFile().getBytes() );
            }
        };
    }
}
