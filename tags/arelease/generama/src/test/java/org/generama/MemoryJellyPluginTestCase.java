package org.generama;

import org.generama.mock.protocol.memory.HandlerTestCase;
import org.generama.tests.AbstractXMLGeneratingPluginTestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Simple example of an AbstractXMLGeneratingPluginTestCase that uses an in-memory
 * Jelly script.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class MemoryJellyPluginTestCase extends AbstractXMLGeneratingPluginTestCase {

    protected Plugin createPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) {
        JellyTemplateEngine templateEngine = new JellyTemplateEngine(){
            protected URL getScriptURL(Class pluginClass, String extension) {
                try {
                    return new URL("memory", null, "" +
                            "<j:jelly xmlns:j='jelly:core'>" +
                            "<jellytest>" +
                            "  <j:forEach var='dood' items='${metadata}'>" +
                            "    <item>${dood}</item>" +
                            "  </j:forEach>" +
                            "</jellytest>" +
                            "</j:jelly>");
                } catch (MalformedURLException e) {
                    HandlerTestCase.assertMemoryUrlProviderIsConfigured();
                    return null;
                }
            }

        };
        return new Plugin(templateEngine, metadataProvider, writerMapper);
    }

    protected MetadataProvider createMetadataProvider() throws Exception {
        return new OneTwoThreeStringMetadataProvider();
    }

    protected URL getExpected() throws MalformedURLException {
        return new URL("memory", null, "" +
                "<jellytest>"
                + "  <item>One</item>"
                + "  <item>Two</item>"
                + "  <item>Three</item>"
                + "</jellytest>");
    }
}
