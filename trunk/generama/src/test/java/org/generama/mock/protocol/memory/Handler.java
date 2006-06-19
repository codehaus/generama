package org.generama.mock.protocol.memory;

import java.net.URLStreamHandler;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

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
    
    /**
     * this method is part of an really ugly hack around classloading
     * problem in m2 / surefire plugin
     *
     */
    public static void initHandler() {
    	try {
       	URL.setURLStreamHandlerFactory( new URLStreamHandlerFactory() {

			public URLStreamHandler createURLStreamHandler(String protocol) {
				if("memory".equals(protocol)){
					return new Handler();
				}
				return null;
			}});
    	} catch(Throwable ex) {
    		// we just ignore this ficking exception, because m2 does not fork properly
    		// and URL does not like double handler definitions. 
    		// blame sun on unflexibility and surefire guys on classloader
    		// wrapping
    	}
    }
}
