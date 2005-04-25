package org.generama.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.Velocity;
import org.generama.velocity.MemoryResourceLoader;

import java.util.Properties;

/**
 * This VelocityComponent returns a VelocityEngine using a {@link MemoryResourceLoader}.
 * The MemoryResourceLoader can be accessed with {@link #getMemoryResourceLoader().
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class MemoryVelocityComponent implements VelocityComponent {
    private VelocityEngine velocityEngine = new VelocityEngine();

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public MemoryVelocityComponent() throws Exception {

        Properties properties = new Properties();
        properties.setProperty( "resource.loader", "memory");
        properties.setProperty( "memory.resource.loader.class", MemoryResourceLoader.class.getName());

        // Use our own resource manager
        properties.setProperty(Velocity.RESOURCE_MANAGER_CLASS, ResourceLoaderExposingResourceManager.class.getName());

        velocityEngine.init(properties);
    }

    public MemoryResourceLoader getMemoryResourceLoader() {
        ResourceLoaderExposingResourceManager rme = ResourceLoaderExposingResourceManager.getInstance();
        MemoryResourceLoader result = (MemoryResourceLoader) rme.getResourceLoaders().get(0);
        return result;
    }

}
