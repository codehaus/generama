package org.generama.defaults;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Resolver which calculates dtd name from systemId and prefix
 * and finds dtd file in base directory.
 * For example if prefix http://nowhere.com/ and basedir is /usr/share/dtds
 * then from systemId http://nowhere.com/test.dtd
 * this Resolver calculates /usr/share/dtds/test.dtd.
 * <p/>
 * It is very useful if you have numerous dtd files and you dont want to enumerate
 * them all using {@link MapPairsEntityResolver}
 * <p/>
 * If file not found then IllegalArgumentException throws
 *
 * @author Anatol Pomozov
 */
class DirEntityResolver implements EntityResolver {
    private static final Log log = LogFactory.getLog(DirEntityResolver.class);
    private File basedir;
    private String prefix;

    /**
     * @param basedir directory where dtd collection is located
     * @param prefix  prefix
     */
    public DirEntityResolver(File basedir, String prefix) {
        if (basedir == null)
            throw new NullPointerException("basedir must not be null");
        if (!basedir.isDirectory())
            throw new IllegalArgumentException("basedir must be valid directory");
        this.basedir = basedir;
        this.prefix = prefix;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (systemId != null) {
            if (systemId.startsWith(prefix)) {
                log.debug("trying to locate " + systemId + " in dir " + basedir);

                // Search for DTD
                String path = systemId.substring(prefix.length());
                InputStream dtdStream = new FileInputStream(new File(basedir, path));

                if (dtdStream == null) {
                    throw new IllegalArgumentException("dtd file ('" + path + "') did not found in '" + basedir + "'");
                } else {
                    log.debug("found " + systemId + " in basedir");
                    InputSource source = new InputSource(dtdStream);
                    source.setPublicId(publicId);
                    source.setSystemId(systemId);
                    return source;
                }
            } else {
                throw new IllegalArgumentException("systemId ('" + systemId + "') does not starts from prefix '" + prefix + "'");
            }
        } else {
            // use the default behaviour
            return null;
        }
    }
}
