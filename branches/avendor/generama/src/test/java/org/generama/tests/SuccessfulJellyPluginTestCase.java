package org.generama.tests;

import org.generama.MetadataProvider;
import org.generama.OneTwoThreeStringMetadataProvider;
import org.generama.Plugin;
import org.generama.WriterMapper;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class SuccessfulJellyPluginTestCase extends AbstractXMLGeneratingPluginTestCase {
    protected Plugin createPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) throws Exception {
        return new TestJellyPlugin(metadataProvider, writerMapper);
    }

    protected URL getExpected() throws MalformedURLException {
        return new URL( "memory", null, "" +
                "<test>" +
                "  <number name=\"One\"/>" +
                "  <number name=\"Two\"/>" +
                "  <number name=\"Three\"/>" +
                "</test>"
        );
    }

    protected MetadataProvider createMetadataProvider() throws Exception {
        return new OneTwoThreeStringMetadataProvider();
    }
}
