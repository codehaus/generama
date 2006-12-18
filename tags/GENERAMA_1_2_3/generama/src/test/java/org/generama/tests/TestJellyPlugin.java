package org.generama.tests;

import org.generama.JellyTemplateEngine;
import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.WriterMapper;
import org.generama.TestMetadataProvider;

import java.util.Collection;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class TestJellyPlugin extends Plugin {
    public TestJellyPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(new JellyTemplateEngine(), metadataProvider, writerMapper);
    }

    protected Collection getMetadata() {
        return ((TestMetadataProvider) metadataProvider).getMetadata();
    }
}
