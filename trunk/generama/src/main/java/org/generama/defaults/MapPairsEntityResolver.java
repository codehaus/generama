package org.generama.defaults;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Resolver which calculates filepath to dtd from systemId of given XML file.
 * If file not found then IllegalArgumentException throws 
 *
 * @author Anatol Pomozov
 */
class MapPairsEntityResolver implements EntityResolver {
    private Map dtds;

    /**
     * @param dtds Map which contain pairs of {systemId ==> full_path_to_dtd}
     */
    public MapPairsEntityResolver(Map dtds) {
        this.dtds = dtds;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        String path =  (String) dtds.get(systemId);
        if (path == null)
            throw new IllegalArgumentException("DTD file not defined for " + systemId);

        InputSource source = new InputSource(new FileInputStream(path));
        if (source == null)
            throw new IllegalArgumentException("DTD not found at " + path);

        source.setPublicId(publicId);
        source.setSystemId(systemId);

        return source;
    }
}
