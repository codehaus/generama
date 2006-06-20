package org.generama.mock.protocol.memory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AccessController;

import junit.framework.TestCase;
import sun.security.action.GetPropertyAction;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class HandlerTestCase extends TestCase {

    public void testMemoryURL() throws IOException {
        assertMemoryUrlProviderIsConfigured();
        URL url = new URL("memory", null, "hello\nworld\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        assertEquals("hello", reader.readLine());
        assertEquals("world", reader.readLine());
    }

    public static void assertMemoryUrlProviderIsConfigured() {
        String protocolPackages = System.getProperty("java.protocol.handler.pkgs");
        
		String fromaction = ((String)(AccessController.doPrivileged
		(new GetPropertyAction("java.protocol.handler.pkgs",
				       ""))));
		
        System.err.println("protocol packages: " + protocolPackages);
        System.err.println("from action: " + fromaction);
        
        
        try {
			System.err.println("class in question:" + Thread.currentThread().getContextClassLoader().loadClass(protocolPackages + ".memory.Handler"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String message = "The \"java.protocol.handler.pkgs\" system property should contain \"org.generama.mock.protocol\" when running tests. See java.net.URL javadocs. It was \"" + protocolPackages + "\"";
        assertNotNull( message, protocolPackages );
        assertTrue( message, protocolPackages.indexOf("org.generama.mock.protocol") != -1 );
    
    }
}
