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
public class VelocityMergePlugin extends Plugin {
    private QDoxCapableMetadataProvider metadataProvider;

    public VelocityMergePlugin(TemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider,writerMapper);
        setMultioutput(false);
        setFilereplace("velo.txt");
        this.metadataProvider = metadataProvider;
    }

    protected Collection getMetadata() {
        return metadataProvider.getMetadata();
    }
}
