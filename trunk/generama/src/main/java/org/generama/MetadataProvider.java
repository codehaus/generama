package org.generama;

import java.util.Collection;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface MetadataProvider {
    public static final String ROLE = MetadataProvider.class.getName();
    
    Collection getMetadata();
    String getOriginalFileName(Object metadata);
    String getOriginalPackageName(Object metadata);
}
