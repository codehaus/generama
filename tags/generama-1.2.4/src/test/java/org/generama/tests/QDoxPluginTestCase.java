package org.generama.tests;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.JavaDocBuilder;
import junit.framework.TestCase;
import org.generama.defaults.QDoxPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Anatol Pomozov
 */
public class QDoxPluginTestCase extends TestCase {
    public void testRestrictedFolder() throws IOException {
        QDoxPlugin qDoxPlugin = new QDoxPlugin(null, null, null) {
        };

        File tempFile = File.createTempFile("xdoclet2", "java");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("package org.generama.tests;\n" +
                "\n" +
                "import java.net.URL;\n" +
                "\n" +
                "/**\n" +
                " * @author Anatol Pomozov\n" +
                " */\n" +
                "public class TestClass {\n" +
                "    private URL hello;\n" +
                "}");
        writer.close();

        qDoxPlugin.setRestrictedpath(tempFile.getParent() + " , sdfsdfs");

        JavaDocBuilder builder = new JavaDocBuilder();
        builder.addSource(tempFile);

        JavaClass klass = builder.getClassByName("org.generama.tests.TestClass");

        assertFalse(qDoxPlugin.shouldGenerate(klass));

        tempFile.delete();
    }
}



