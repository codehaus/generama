package org.generama;

import java.util.Collection;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class TestFreemarkerPlugin extends Plugin {
    public TestFreemarkerPlugin(FreeMarkerTemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider, writerMapper);
    }

    protected Collection getMetadata() {
        return ((TestMetadataProvider) metadataProvider).getMetadata();
    }
}