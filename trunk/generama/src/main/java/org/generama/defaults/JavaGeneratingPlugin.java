package org.generama.defaults;

import org.generama.MetadataProvider;
import org.generama.Plugin;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JavaGeneratingPlugin extends Plugin {

    protected JavaGeneratingPlugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider, writerMapper);
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
