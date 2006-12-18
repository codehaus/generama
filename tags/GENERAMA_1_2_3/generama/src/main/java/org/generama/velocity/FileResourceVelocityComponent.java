/*
 * Copyright (c) 2005
 * XDoclet Team
 * All rights reserved.
 */
package org.generama.velocity;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * This VelocityComponent returns a VelocityEngine loading templates from a list of directories.
 *
 * @author Diogo Quintela
 * @version $Revision$
 */
public class FileResourceVelocityComponent implements MergeableVelocityComponent {
    private VelocityEngine velocityEngine = new VelocityEngine();

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public FileResourceVelocityComponent() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
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
        return (ConfigurableFileResourceLoader) rme.getResourceLoaders().get(0);
    }

    public void addPath(String path) {
        getFileResourceLoader().addPath(path);
    }

    public void clearPaths() {
        getFileResourceLoader().clearPaths();
    }
}
