package org.generama.mock.protocol.memory;

import junit.framework.TestCase;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

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
        String message = "The \"java.protocol.handler.pkgs\" system property should contain \"org.generama.mock.protocol\" when running tests. See java.net.URL javadocs. It was \"" + protocolPackages + "\"";
        assertNotNull( message, protocolPackages );
        assertTrue( message, protocolPackages.indexOf("org.generama.mock.protocol") != -1 );
    }
}
