package org.generama.velocity;

import org.apache.velocity.app.VelocityEngine;

/**
 * A PicoContainer compliant component that can provide a {@link VelocityEngine}.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface VelocityComponent {
    VelocityEngine getVelocityEngine() throws Exception;
}
