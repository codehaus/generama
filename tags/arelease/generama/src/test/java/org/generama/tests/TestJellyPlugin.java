package org.generama.tests;

import org.generama.JellyTemplateEngine;
import org.generama.WriterMapper;
import org.generama.MetadataProvider;
import org.generama.Plugin;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class TestJellyPlugin extends Plugin {
    public TestJellyPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(new JellyTemplateEngine(), metadataProvider, writerMapper);

    }
}
