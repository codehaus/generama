package org.generama;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.generama.velocity.ClasspathVelocityComponent;
import org.generama.velocity.VelocityComponent;

import java.io.Writer;
import java.util.Map;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class VelocityTemplateEngine implements TemplateEngine {
    private VelocityComponent velocityComponent;
    private String templateName;

    public VelocityTemplateEngine(VelocityComponent velocityComponent) {
        this.velocityComponent = velocityComponent;
    }

    public VelocityTemplateEngine() {
        this(new ClasspathVelocityComponent());
    }

    public String getTemplatename() {
        return templateName;
    }

    public void setTemplatename(String templateName) {
        this.templateName = templateName;
    }

    public void generate(Writer out, Map contextObjects, String encoding, Class pluginClass) throws GeneramaException {
        VelocityContext context = new VelocityContext(contextObjects);
        if (templateName == null) {
            templateName = getScriptPath(getUnqualifiedClassName(pluginClass) + ".vm", pluginClass);
        }
        try {
            VelocityEngine velocityEngine = velocityComponent.getVelocityEngine();
            velocityEngine.mergeTemplate(templateName, context, out);
            out.flush();
        } catch (MethodInvocationException e) {
            Throwable cause = e.getWrappedThrowable() != null ? e.getWrappedThrowable() : e;
            throw new GeneramaException("Exception occurred when running Velocity", cause);
        } catch (Exception e) {
            throw new GeneramaException("Exception occurred when running Velocity", e);
        }
    }

    private String getUnqualifiedClassName(Class pluginClass) {
        String className = pluginClass.getName();
        int unqualifiedNameStart = className.lastIndexOf('.') + 1;
        String result = className.substring(unqualifiedNameStart);
        return result;
    }


    protected String getScriptPath(String scriptName, Class pluginClass) {
        String packageName = Plugin.getPackageName(pluginClass);
        String scriptPath = packageName.replace('.', '/') + "/" + scriptName;
        return scriptPath;
    }

}
