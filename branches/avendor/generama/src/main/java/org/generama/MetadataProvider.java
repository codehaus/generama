package org.generama;

import java.util.Collection;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface MetadataProvider {
    Collection getMetadata();
    String getOriginalFileName(Object metadata);
    String getOriginalPackageName(Object metadata);
}
