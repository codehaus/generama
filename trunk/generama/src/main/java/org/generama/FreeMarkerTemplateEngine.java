package org.generama;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FreeMarkerTemplateEngine extends AbstractTemplateEngine {
    private final Configuration cfg;

    public FreeMarkerTemplateEngine() {
        this.cfg = new Configuration();
        cfg.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateEngine.class, "/"));
    }

    public void generate(Writer out, Map contextObjects, String encoding, Class pluginClass) {
        try {
            final String scriptPath = getScriptPath(pluginClass, ".ftl");
            final Template template = cfg.getTemplate(scriptPath, encoding);
            template.process(contextObjects, out);
            out.flush();
        } catch (IOException e) {
            throw new GeneramaException(e.getMessage(), e);
        } catch (TemplateException e) {
            throw new GeneramaException(e.getMessage(), e);
        }
    }
}