package org.generama;



/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface MetadataProvider {
    public static final String ROLE = MetadataProvider.class.getName();
    
    String getOriginalFileName(Object metadata);
    String getOriginalPackageName(Object metadata);
    ConfigurableDocletTagFactory getDocletTagFactory();
}
