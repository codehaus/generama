package org.generama.tests;

import junit.framework.AssertionFailedError;
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
public class FailingTextGeneratingVelocityPluginTestCase extends AbstractTextGeneratingPluginTestCase {
    // overriding this method, because we expect it to fail
    public void testGenerateContent() throws Throwable {
        try {
            super.testGenerateContent();
            fail("Should have failed");
        } catch (AssertionFailedError e) {
            // Expected
        }
    }

    protected Plugin createPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) throws Exception {
        return new BuggyCsvPlugin(metadataProvider, writerMapper);
    }

    protected MetadataProvider createMetadataProvider() {
        return new OneTwoThreeStringMetadataProvider();
    }

    protected URL getExpected() throws MalformedURLException {
        return new URL("memory", null, "One,Two,Three");
    }
}
