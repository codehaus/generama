package org.generama.defaults;

import org.generama.QDoxCapableMetadataProvider;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;


/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JavaGeneratingPlugin extends QDoxPlugin {

    protected JavaGeneratingPlugin(TemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
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
