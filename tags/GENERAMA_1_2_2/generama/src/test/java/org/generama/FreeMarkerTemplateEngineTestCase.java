package org.generama;

import java.net.MalformedURLException;
import java.net.URL;

import org.generama.tests.AbstractTextGeneratingPluginTestCase;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FreeMarkerTemplateEngineTestCase extends AbstractTextGeneratingPluginTestCase {
	
	
    public FreeMarkerTemplateEngineTestCase() {
		super();
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
