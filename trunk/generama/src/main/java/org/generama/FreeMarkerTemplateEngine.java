package org.generama;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FreeMarkerTemplateEngine extends AbstractTemplateEngine {
    public void generate(Writer out, Map contextObjects, String encoding, Class pluginClass) {
        URL url = getScriptURL(pluginClass, ".ftl");
        try {
            InputStream scriptStream = url.openStream();
            Reader reader = new InputStreamReader(scriptStream);
            Template template = new Template(url.toExternalForm(), reader, Configuration.getDefaultConfiguration(), encoding);
            template.process(contextObjects, out);
            out.flush();
        } catch (IOException e) {
            throw new GeneramaException(e.getMessage(), e);
        } catch (TemplateException e) {
            throw new GeneramaException(e.getMessage(), e);
        }
    }
}