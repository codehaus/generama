package org.generama.velocity;

import org.apache.velocity.runtime.resource.ResourceManagerImpl;

import java.util.List;

/**
 * A {@link org.apache.velocity.runtime.resource.ResourceManager} that exposes
 * the registered {@link org.apache.velocity.runtime.resource.loader.ResourceLoader}s.
 * <p>
 * This class is implemented as a singleton to circumvent the (sad?) fact that
 * Velocity doesn't provide a way to access its ResourceLoaders. Velocity instantiates
 * its ResourceManager with reflection, but doesn't expose them afterwards. The
 * singleton is set in the constructor.
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class ResourceLoaderExposingResourceManager extends ResourceManagerImpl {
    private static ResourceLoaderExposingResourceManager instance;

    static ResourceLoaderExposingResourceManager getInstance() {
        return instance;
    }

    public ResourceLoaderExposingResourceManager() {
        instance = this;
    }

    public List getResourceLoaders() {
        return resourceLoaders;
    }
}
