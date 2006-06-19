package org.generama;

import org.generama.mock.protocol.memory.Handler;
import org.generama.tests.AbstractTextGeneratingPluginTestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FreeMarkerTemplateEngineTestCase extends AbstractTextGeneratingPluginTestCase {
	
	public FreeMarkerTemplateEngineTestCase() {
		super();
		Handler.initHandler();
	}
    protected Plugin createPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) throws Exception {
        return new TestFreemarkerPlugin(new FreeMarkerTemplateEngine(), metadataProvider, writerMapper);
    }

    protected MetadataProvider createMetadataProvider() throws Exception {
        return new OneTwoThreeStringMetadataProvider();
    }

    protected URL getExpected() throws MalformedURLException {
        return new URL("memory", null, ""
                + "freemarkertest\n"
                + "o One\n"
                + "o Two\n"
                + "o Three\n");
    }
}
