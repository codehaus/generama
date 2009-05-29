package org.generama.defaults;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Resolver which calculates filepath to dtd from systemId of given XML file.
 * If file not found then IllegalArgumentException throws 
 *
 * @author Anatol Pomozov
 */
class MapPairsEntityResolver implements EntityResolver {
    private Map dtds;

    /**
     * @param dtds Map which contain pairs of {systemId ==> url_to_dtd}
     */
    public MapPairsEntityResolver(Map dtds) {
        this.dtds = dtds;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws IOException {
        URL dtdUrl =  (URL) dtds.get(systemId);
        if (dtdUrl == null)
            throw new IllegalArgumentException("DTD file not defined for " + systemId);

        InputSource source = new InputSource(dtdUrl.openStream());

        source.setPublicId(publicId);
        source.setSystemId(systemId);

        return source;
    }
}
