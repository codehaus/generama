package org.generama.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.Properties;

/**
 * This VelocityComponent returns a VelocityEngine loading templates from the classpath.
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class ClasspathVelocityComponent implements VelocityComponent {
    private VelocityEngine velocityEngine = new VelocityEngine();

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public ClasspathVelocityComponent() {

        Properties properties = new Properties();
        properties.setProperty( "resource.loader", "classpath");
        properties.setProperty( "classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        try {
            velocityEngine.init(properties);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
