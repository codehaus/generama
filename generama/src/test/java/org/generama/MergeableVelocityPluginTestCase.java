/*
 * Copyright (c) 2005
 * XDoclet Team
 * All rights reserved.
 */
package org.generama;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.generama.tests.AbstractXMLGeneratingPluginTestCase;
import org.generama.velocity.ClasspathFileResourceVelocityComponent;

/**
 * @author Diogo Quintela
 */
public class MergeableVelocityPluginTestCase extends AbstractXMLGeneratingPluginTestCase {

    protected Plugin createPlugin(final MetadataProvider metadataProvider, WriterMapper writerMapper)
        throws Exception {
        String path;
        assertNotNull(path = System.getProperty("basedir"));

        ClasspathFileResourceVelocityComponent velocityComponent = new ClasspathFileResourceVelocityComponent();
        velocityComponent.addPath(path);

        MergeableVelocityTemplateEngine templateEngine = new MergeableVelocityTemplateEngine(velocityComponent) {
            public String getScriptPath(String scriptName, Class pluginClass) {
                String packageName = Plugin.getPackageName(MergeableVelocityPluginTestCase.class);
                return packageName.replace('.', '/') + "/MergeableVelocityPluginTestCase.vm";
            }
        };

        return new Plugin(templateEngine, metadataProvider, writerMapper) {
            protected Collection getMetadata() {
                return ((TestMetadataProvider)metadataProvider).getMetadata();
            }
        };
    }

    protected MetadataProvider createMetadataProvider() throws Exception {
        return new OneTwoThreeStringMetadataProvider();
    }

    protected URL getExpected() throws Exception {
        String path;
        assertNotNull(path = System.getProperty("basedir"));
        return new File(path, "project.xml").toURL();
    }
}
