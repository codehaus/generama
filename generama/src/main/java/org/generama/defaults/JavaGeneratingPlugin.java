package org.generama.defaults;

import org.generama.Plugin;
import org.generama.QDoxCapableMetadataProvider;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;

import java.util.Collection;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JavaGeneratingPlugin extends Plugin {
    private QDoxCapableMetadataProvider metadataProvider;

    protected Collection getMetadata() {
        return metadataProvider.getMetadata();
    }

    protected JavaGeneratingPlugin(TemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider, writerMapper);
        this.metadataProvider = metadataProvider;
    }

    public String getDestinationClassname(Object metadata) {
        String destinationFilename = getDestinationFilename(metadata);
        return destinationFilename.substring(0, destinationFilename.indexOf('.'));
    }

    public String getDestinationFullyQualifiedClassName(Object metadata) {
        String packageName = getDestinationPackage(metadata);
        packageName = packageName.equals("") ? "" : packageName + ".";
        return packageName + getDestinationClassname(metadata);
    }
}
