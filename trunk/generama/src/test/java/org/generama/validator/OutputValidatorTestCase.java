package org.generama.validator;

import junit.framework.TestCase;
import org.generama.*;
import org.generama.tests.SinkWriterMapper;
import org.generama.defaults.XMLOutputValidator;
import org.generama.tests.TestJellyPlugin;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anatol Pomozov
 */
public class OutputValidatorTestCase extends TestCase {
    private Map dtds;

    protected void setUp() throws Exception {
        dtds = new HashMap();
        String path = new File(getClass().getResource(".").getPath(), "the.dtd").getCanonicalPath();
        dtds.put("http://nowhere.com/the.dtd", path);
    }

    public void testBuggyValidator() {
        try {
            MetadataProvider metadataProvider = new OneTwoThreeStringMetadataProvider();
            WriterMapper writerMapper = new SinkWriterMapper();
            TestJellyPlugin plugin = new TestJellyPlugin(metadataProvider, writerMapper);
            plugin.setValidate(true);
            plugin.setOutputValidator(new OutputValidator() {
                public void validate(URL url) throws OutputValidationError {
                    assertEquals("URL Schema", "string", url.getProtocol());
                    throw new OutputValidationError("This is validator error", url);
                }
            });
            System.err.println("starting plugin");
            plugin.start();

            fail("There must be Runtime exception");
        } catch (RuntimeException e) {
        }
    }

    public void testValidXML() throws IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<!DOCTYPE top SYSTEM \"http://nowhere.com/the.dtd\">" +
                "<top name=\"root\">" +
                "    <child resource=\"a1\" file=\"f1\" jar=\"jar1\"/>" +
                "    <child resource=\"a2\" file=\"f2\" jar=\"jar2\"/>" +
                "</top>";
        validate(xml);
    }

    public void testInvalidXML() throws IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<!DOCTYPE top SYSTEM \"http://nowhere.com/the.dtd\">" +
                "<top>" +
                "    <child resource=\"a1\" file=\"f1\" jar=\"jar1\"/>" +
                "    <child resource=\"a2\" file=\"f2\" jar=\"jar2\"/>" +
                "</top>";
        try {
            validate(xml);
            fail("There must be RuntimeException");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().indexOf("Attribute value for \"name\" is #REQUIRED.") != 0);
        }
    }

    private void validate(final String xml) throws IOException {
        MetadataProvider provider = new OneTwoThreeStringMetadataProvider();
        WriterMapper mapper = new SinkWriterMapper();

        TemplateEngine engine = new TemplateEngine(){
            public void generate(Writer out, Map contextObjects, String encoding, Class pluginClass) throws GeneramaException {
                try {
                    out.write(xml);
                } catch (IOException e) {
                    fail("There must not be exception");
                }
            }
        };

        TestPlugin plugin = new TestPlugin(engine, provider, mapper);
        plugin.setValidate(true);
        plugin.setOutputValidator(new XMLOutputValidator(dtds));
        plugin.start();
    }

    private class TestPlugin extends Plugin {
        public TestPlugin(TemplateEngine engine, MetadataProvider provider, WriterMapper mapper) {
            super(engine, provider, mapper);
        }

        protected Collection getMetadata() {
            return ((TestMetadataProvider) metadataProvider).getMetadata();
        }
    }
}
