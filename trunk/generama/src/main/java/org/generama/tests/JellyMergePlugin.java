package org.generama.tests;

import org.generama.Plugin;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;
import org.generama.QDoxCapableMetadataProvider;

import java.util.Collection;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JellyMergePlugin extends Plugin {
    QDoxCapableMetadataProvider metadataProvider;

    public JellyMergePlugin(TemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider,writerMapper);
        setMultioutput(false);
        setFilereplace("jelly.txt");
        this.metadataProvider = metadataProvider;
    }

    protected Collection getMetadata() {
        return metadataProvider.getMetadata();
    }
}
