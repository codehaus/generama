package org.generama.tests;

import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JellyMergePlugin extends Plugin {
    public JellyMergePlugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider,writerMapper);
        setMultioutput(false);
        setFilereplace("jelly.txt");
    }
}
