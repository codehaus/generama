package org.generama.mock.protocol.memory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * This handler is used to mock URLs from memory.
 * 
 * @author Aslak Helles&oslash;y
 * @author Konstantin Pribluda
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
