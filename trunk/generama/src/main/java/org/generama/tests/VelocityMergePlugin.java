package org.generama.tests;

import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class VelocityMergePlugin extends Plugin {
    public VelocityMergePlugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider,writerMapper);
        setMultioutput(false);
        setFilereplace("velo.txt");
    }
}
