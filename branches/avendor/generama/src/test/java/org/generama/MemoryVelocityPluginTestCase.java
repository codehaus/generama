package org.generama;

import org.generama.tests.AbstractTextGeneratingPluginTestCase;
import org.generama.velocity.MemoryVelocityComponent;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class MemoryVelocityPluginTestCase extends AbstractTextGeneratingPluginTestCase {
    protected Plugin createPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) throws Exception {
        MemoryVelocityComponent velocityComponent = new MemoryVelocityComponent();

        velocityComponent.getMemoryResourceLoader().addScript("test", ""
                + "velocitytest\n"
                + "#foreach( $dood in ${metadata} )\n"
                + "o ${dood}\n"
                + "#end");

        VelocityTemplateEngine templateEngine = new VelocityTemplateEngine(velocityComponent) {
            public String getScriptPath(String scriptName, Class pluginClass) {
                return "test";
            }
        };
        return new Plugin(templateEngine, metadataProvider, writerMapper);
    }

    protected MetadataProvider createMetadataProvider() throws Exception {
        return new OneTwoThreeStringMetadataProvider();
    }

    protected URL getExpected() throws MalformedURLException {
        return new URL("memory", null, ""
                + "velocitytest\n"
                + "o One\n"
                + "o Two\n"
                + "o Three\n");
    }
}
