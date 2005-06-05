package org.generama.tests;

import junit.framework.TestCase;
import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.WriterMapper;
import org.generama.defaults.Outcome;

import java.net.URL;

/**
 * Abstract test case for a plugin. A subclass should be made for each plugin.
 * 
 * @author Aslak Helles&oslash;y
 */
public abstract class AbstractPluginTestCase extends TestCase {
    protected Plugin plugin;
    private SinkWriterMapper writerMapper;

    protected void setUp() throws Exception {
        writerMapper = new SinkWriterMapper();
        plugin = createPlugin(createMetadataProvider(), writerMapper);
    }

    public void testGenerateContent() throws Throwable {
        try {
            plugin.start();
            Outcome out = writerMapper.getOutcome(null, null);
            compare(getExpected(), out.getURL());
        } catch (Throwable e) {
            // print out all of it
            System.out.println("**** START GENERATED CONTENT ****");
            System.out.println(writerMapper.getContent());
            System.out.println("**** END GENERATED CONTENT ****");
            throw e;
        }
        // assert that the content was written to a file
    }

    protected abstract Plugin createPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) throws Exception;
    protected abstract MetadataProvider createMetadataProvider() throws Exception;
    protected abstract URL getExpected() throws Exception;
    protected abstract void compare(URL expected, URL actual) throws Exception;

    /**
     * @deprecated "use {@link #getResourceURLRelativeToThisPackage(String)}"
     */
    protected URL getResourceURLRelativeToThisPackage(String resourceName) {
        return getResourceRelativeToThisPackage(resourceName);
    }

    /**
     * Helper method for subclasses that wish to retrieve a test resource without
     * typing the full path. Handy for accessing e.g. java sources for XDoclet tests.
     *
     * @param resourceName
     * @return
     */
    protected URL getResourceRelativeToThisPackage(String resourceName) {
        String className = getClass().getName();
        String packageName = className.substring(0, className.lastIndexOf('.'));
        String resourcePath = "/" + packageName.replace('.', '/' ) + "/" + resourceName;
        URL resource = getClass().getResource(resourcePath);
        assertNotNull("Resource not found at path: " + resourcePath, resource);
        return resource;
    }
}
