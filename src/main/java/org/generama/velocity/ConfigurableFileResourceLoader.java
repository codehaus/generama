/*
 * Copyright (c) 2005
 * XDoclet Team
 * All rights reserved.
 */
package org.generama.velocity;

import java.util.Vector;

import org.apache.commons.collections.ExtendedProperties;

import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

/**
 * This ResourceLoader allows for a set of paths to be scanned for resources.
 *
 * @author Diogo Quintela
 * @version $Revision$
 */
public class ConfigurableFileResourceLoader extends FileResourceLoader {
    protected Vector paths;

    public void addPath(String path) {
        paths.add(path);
    }

    public void clearPaths() {
        paths.clear();
    }

    public void init(ExtendedProperties configuration) {
        // paths Vector is the same object instance used in FileResourceLoader
        paths = configuration.getVector("path");
        paths.clear();
        super.init(configuration);
    }
}
