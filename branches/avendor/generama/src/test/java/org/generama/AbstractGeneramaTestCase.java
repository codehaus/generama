package org.generama;

import junit.framework.TestCase;
import org.nanocontainer.integrationkit.ContainerBuilder;
import org.nanocontainer.integrationkit.DefaultLifecycleContainerBuilder;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.ObjectReference;
import org.picocontainer.defaults.SimpleReference;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
// TODO: extend some AbstractContainerBuilderTestCase ?
public abstract class AbstractGeneramaTestCase extends TestCase {
    private MutablePicoContainer pico;
    private ContainerBuilder builder;
    private ObjectReference picoRef;

    protected abstract Generama createGeneramaWithThreeMetadataObjects();

    protected void setUp() {
        Generama generama = createGeneramaWithThreeMetadataObjects();
        builder = new DefaultLifecycleContainerBuilder(generama);
        picoRef = new SimpleReference();
        builder.buildContainer(picoRef, null, null);
        pico = (MutablePicoContainer) picoRef.get();;
    }

    public void testSingleOutputPlugin() {

        pico.registerComponentImplementation(SingleOutputPlugin.class);

        // subclasses must remember to configure this one when they create a Generama
        SingleOutputPlugin plugin = (SingleOutputPlugin) pico.getComponentInstance(SingleOutputPlugin.class);

        builder.buildContainer(picoRef, null, null);
        pico = (MutablePicoContainer) picoRef.get();

        assertEquals(1, plugin.getGenerateCount());
    }

    public void testMultiOutputPlugin() {

        pico.registerComponentImplementation(MultiOutputPlugin.class);

        // subclasses must remember to configure this one when they create a Generama
        MultiOutputPlugin plugin = (MultiOutputPlugin) pico.getComponentInstance(MultiOutputPlugin.class);

        builder.buildContainer(picoRef, null, null);
        assertEquals(3, plugin.getGenerateCount());
    }

    public static abstract class TestOutputPlugin extends Plugin {
        private int generateCount;

        public TestOutputPlugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
            super(templateEngine, metadataProvider, writerMapper);
        }

        public int getGenerateCount() {
            return generateCount;
        }

        public void generate(Writer out, Object metadata) throws IOException, GeneramaException {
            out.write(metadata.toString());
            generateCount++;
        }
    }

    public static class MultiOutputPlugin extends TestOutputPlugin {
        public MultiOutputPlugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
            super(templateEngine, metadataProvider, writerMapper);
            setMultioutput(true);
        }
    }

    public static class SingleOutputPlugin extends TestOutputPlugin {
        public SingleOutputPlugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
            super(templateEngine, metadataProvider, writerMapper);
            setMultioutput(false);
        }
    }
}
