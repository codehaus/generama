/*
 * Copyright (c) 2005
 * XDoclet Team
 * All rights reserved.
 */
package org.generama.velocity;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * This VelocityComponent returns a VelocityEngine loading templates from the classpath and a set of user
 * specified directories.
 *
 * @author Diogo Quintela
 * @version $Revision$
 */
public class ClasspathFileResourceVelocityComponent implements MergeableVelocityComponent {
    private VelocityEngine velocityEngine = new VelocityEngine();

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public ClasspathFileResourceVelocityComponent() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "classpath, file");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        properties.setProperty("file.resource.loader.class", ConfigurableFileResourceLoader.class.getName());

        // Use our own resource manager
        properties.setProperty(Velocity.RESOURCE_MANAGER_CLASS, ResourceLoaderExposingResourceManager.class.getName());

        try {
            velocityEngine.init(properties);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ConfigurableFileResourceLoader getFileResourceLoader() {
        ResourceLoaderExposingResourceManager rme = ResourceLoaderExposingResourceManager.getInstance();
        return (ConfigurableFileResourceLoader) rme.getResourceLoaders().get(1);
    }

    public void addPath(String path) {
        getFileResourceLoader().addPath(path);
    }

    public void clearPaths() {
        getFileResourceLoader().clearPaths();
    }
}
