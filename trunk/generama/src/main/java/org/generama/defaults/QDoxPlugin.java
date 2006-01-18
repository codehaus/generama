package org.generama.defaults;


import com.thoughtworks.qdox.model.JavaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.generama.Plugin;
import org.generama.QDoxCapableMetadataProvider;
import org.generama.TemplateEngine;
import org.generama.WriterMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Konstantin Pribluda
 * @version $Revision$
 */
public abstract class QDoxPlugin extends Plugin {
    private static final Log log = LogFactory.getLog(QDoxPlugin.class);

    protected QDoxCapableMetadataProvider metadataProvider;
    private List restrictedFolders = new ArrayList();

    public QDoxPlugin(TemplateEngine templateEngine, QDoxCapableMetadataProvider metadataProvider, WriterMapper writerMapper) {
        super(templateEngine, metadataProvider, writerMapper);
        this.metadataProvider = metadataProvider;

    }

    public void setRestrictedpath(String folderList) throws IOException {
        if (folderList == null)
            throw new IllegalArgumentException("Restricted Folders list could not be null");

        String[] folders = folderList.split(",");

        for (int i = 0; i < folders.length; i++) {
            String folder = folders[i].trim();
            if (folder.length() > 0) {
                String f = new File(folder).getCanonicalPath();
                restrictedFolders.add(f);
                if (log.isDebugEnabled())
                    log.debug("Folder " + f + " added to restricted");
            }
        }
    }

    protected QDoxCapableMetadataProvider getMetadataProvider() {
        return this.metadataProvider;
    }

    protected Collection getMetadata() {
        return metadataProvider.getMetadata();
    }

    public boolean shouldGenerate(Object metadata) {
        return !inRestrictedFolder((JavaClass) metadata);
    }

    private boolean inRestrictedFolder(JavaClass javaClass) {
        if (restrictedFolders.isEmpty()) {
            return false;
        }

        String sourcePath = javaClass.getSource().getURL().getFile();
            for (int i = 0; i < restrictedFolders.size(); i++) {
                String folder = (String) restrictedFolders.get(i);
            if (isIn(sourcePath, folder)) {
                    return true;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("File " + sourcePath + " not in restricted folder");
        }
        return false;
    }
}
