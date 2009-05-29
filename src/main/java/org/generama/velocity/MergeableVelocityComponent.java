/*
 * Copyright (c) 2005
 * XDoclet Team
 * All rights reserved.
 */
package org.generama.velocity;

import org.apache.velocity.app.VelocityEngine;

/**
 * A PicoContainer compliant component that can provide a {@link VelocityEngine}
 * and the ability to insert paths into it's loader.
 *
 * @author Diogo Quintela
 * @version $Revision$
 */
public interface MergeableVelocityComponent extends VelocityComponent {
    void addPath(String path) throws Exception;
    void clearPaths();
}
