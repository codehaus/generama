package org.generama;

import org.generama.velocity.ClasspathFileResourceVelocityComponent;
import org.nanocontainer.integrationkit.ContainerComposer;
import org.picocontainer.MutablePicoContainer;

/**
 * This class installs the core Generama components in a <a href="http://www.picocontainer.org/">PicoContainer</a>
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class Generama implements ContainerComposer {
    private Class metadataProviderClass;
    private Class writerMapperClass;

    public Generama(Class metadataProviderClass, Class writerMapperClass) {
        this.metadataProviderClass = metadataProviderClass;
        this.writerMapperClass = writerMapperClass;
    }

    public void composeContainer(MutablePicoContainer pico, Object assemblyScope) {
        pico.registerComponentImplementation(ClasspathFileResourceVelocityComponent.class);
        pico.registerComponentImplementation(metadataProviderClass);
        pico.registerComponentImplementation(writerMapperClass);
        pico.registerComponentImplementation(JellyTemplateEngine.class);
        pico.registerComponentImplementation(VelocityTemplateEngine.class);
    }
}
