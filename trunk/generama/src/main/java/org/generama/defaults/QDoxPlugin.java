package org.generama.defaults;


import org.generama.Plugin;
import org.generama.QDoxCapableMetadataProvider;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;

import java.util.Collection;

/**
 * @author Konstantin Pribluda
 * @version $Revision$
 */
public abstract class QDoxPlugin extends Plugin  {
    
    private QDoxCapableMetadataProvider metadataProvider;

    public QDoxPlugin(TemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider, writerMapper);
        this.metadataProvider = metadataProvider;
        
    }
    
    protected QDoxCapableMetadataProvider getMetadataProvider() {
        return this.metadataProvider;
    }
    protected Collection getMetadata() {
        return metadataProvider.getMetadata();
    }

}
