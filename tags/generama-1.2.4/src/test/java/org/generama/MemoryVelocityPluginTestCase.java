package org.generama;

import org.generama.tests.AbstractTextGeneratingPluginTestCase;
import org.generama.velocity.MemoryVelocityComponent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class MemoryVelocityPluginTestCase extends AbstractTextGeneratingPluginTestCase {
    protected Plugin createPlugin(MetadataProvider provider, WriterMapper writerMapper) throws Exception {
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
        return new Plugin(templateEngine, provider, writerMapper) {
            protected Collection getMetadata() {
                return ((TestMetadataProvider)metadataProvider).getMetadata();
            }
        } ;
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
