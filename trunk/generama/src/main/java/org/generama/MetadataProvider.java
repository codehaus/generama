package org.generama;

import com.thoughtworks.qdox.model.DocletTagFactory;

import java.util.Collection;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface MetadataProvider {
    public static final String ROLE = MetadataProvider.class.getName();
    
    Collection getMetadata() throws GeneramaException;
    String getOriginalFileName(Object metadata);
    String getOriginalPackageName(Object metadata);
    DocletTagFactory getDocletTagFactory();
}
