package org.generama.tests;

import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.VelocityTemplateEngine;
import org.generama.WriterMapper;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class BuggyCsvPlugin extends Plugin {
    public BuggyCsvPlugin(MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(new VelocityTemplateEngine(), metadataProvider, writerMapper);
    }
}
