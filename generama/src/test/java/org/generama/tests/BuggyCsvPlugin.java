package org.generama.tests;

import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;
import org.generama.TestMetadataProvider;

import java.util.Collection;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class BuggyCsvPlugin extends Plugin {
    public BuggyCsvPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(new VelocityTemplateEngine(), metadataProvider, writerMapper);
    }

    protected Collection getMetadata() {
        return ((TestMetadataProvider) metadataProvider).getMetadata();
    }
}
