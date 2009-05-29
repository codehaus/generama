package org.generama.velocity;

import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.commons.collections.ExtendedProperties;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.HashMap;

/**
 * A ResourceLoader that makes it possible to add Velocity scripts
 * dynamically as in-memory Strings via the {@link #addScript(String, String)
 * method.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class MemoryResourceLoader extends ResourceLoader {
    private Map scripts = new HashMap();

    public void init(ExtendedProperties extendedProperties) {
    }

    public InputStream getResourceStream(String id) throws ResourceNotFoundException {
        String script = (String) scripts.get(id);
        if( script == null ) {
            throw new ResourceNotFoundException("No registered script for id = " + id);
        }
        return new ByteArrayInputStream(script.getBytes());
    }

    public boolean isSourceModified(Resource resource) {
        return false;
    }

    public long getLastModified(Resource resource) {
        return Long.MAX_VALUE;
    }

    public void addScript(String id, String script) {
        scripts.put(id,script);
    }
}
