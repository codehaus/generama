/*
 * Copyright (c) 2005
 * XDoclet Team
 * All rights reserved.
 */
package org.generama;

import java.io.Writer;

import java.util.Map;

import org.generama.velocity.ClasspathFileResourceVelocityComponent;
import org.generama.velocity.MergeableVelocityComponent;

/**
 * A template engine for Velocity that has also the ability to insert paths into it's loader.
 *
 * @author Diogo Quintela
 * @version $Revision$
 */
public class MergeableVelocityTemplateEngine extends VelocityTemplateEngine {
    private MergeableVelocityComponent velocityComponent;

    public MergeableVelocityTemplateEngine(MergeableVelocityComponent velocityComponent) {
        super(velocityComponent);
        this.velocityComponent = velocityComponent;
    }

    public MergeableVelocityTemplateEngine() {
        this(new ClasspathFileResourceVelocityComponent());
    }

    /**
     * Add a path (a directory to serve as an available source for velocity templates).
     * Note: Paths added will be available for the next generate, as after each generate
     * the path list is cleared (we do this to avoid several invocations use dirty paths lists)
     *
     * @param path The path to add
     *
     * @throws Exception this should not be thrown (exists in interface just in case..)
     */
    public void addPath(String path) throws Exception {
        velocityComponent.addPath(path);
    }

    /**
     * Generate, then clears the path list
     */
    public void generate(Writer out, Map contextObjects, String encoding, Class pluginClass)
        throws GeneramaException {
        try {
            super.generate(out, contextObjects, encoding, pluginClass);
        } finally {
            velocityComponent.clearPaths();
        }
    }
}
