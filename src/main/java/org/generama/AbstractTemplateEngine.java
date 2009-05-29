package org.generama;

import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {
    protected URL getScriptURL(Class pluginClass, String extension) {
        String scriptPath = getScriptPath(pluginClass, extension);
        URL result = pluginClass.getResource(scriptPath);
        if (result == null) {
            throw new RuntimeException("Couldn't load resource at path " + scriptPath);
        }
        return result;
    }

    protected String getScriptPath(Class pluginClass, String extension) {
        String scriptPath = "/" + pluginClass.getName().replace('.', '/') + extension;
        return scriptPath;
    }
}