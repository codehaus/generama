package org.generama;

import com.thoughtworks.qdox.model.DocletTagFactory;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Very simple implementation of MetadataProvider used throughout the test suite.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class OneTwoThreeStringMetadataProvider implements MetadataProvider {
    public Collection getMetadata() {
        Collection result = new ArrayList();
        result.add("One");
        result.add("Two");
        result.add("Three");
        return result;
    }

    public String getOriginalFileName(Object metadata) {
        return metadata.toString();
    }

    public String getOriginalPackageName(Object metadata) {
        return metadata.toString();
    }

    public DocletTagFactory getDocletTagFactory() {
        return null;
    }
}
