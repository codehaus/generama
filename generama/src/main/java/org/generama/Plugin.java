package org.generama;

import org.picocontainer.Startable;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class Plugin implements Startable {
    protected static final String DONTEDIT = "Generated file. Do not edit.";

    protected WriterMapper writerMapper;
    protected MetadataProvider metadataProvider;

    private File destdir;
    private String packageregex = "";
    private String packagereplace = "";
    private String fileregex = "";
    private String filereplace = "";
    private String encoding = System.getProperty("file.encoding");
    private boolean isMultioutput = false;
    private final TemplateEngine templateEngine;
    private Map contextObjects = new HashMap();

    public Plugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
        this.templateEngine = templateEngine;
        this.metadataProvider = metadataProvider;
        this.writerMapper = writerMapper;
        setEncoding("ISO-8859-1");
    }

    public boolean shouldGenerate(Object metadata) {
        return true;
    }

    public boolean isMultioutput() {
        return isMultioutput;
    }

    public void setMultioutput(boolean multioutput) {
        isMultioutput = multioutput;
    }

    /**
     * @param fileregex a regular expression indicating
     * what parts of each metadata object's file name
     * should be replaced in the output file.
     */
    public void setFileregex(String fileregex) {
		if(fileregex == null) throw new NullPointerException();
        this.fileregex = fileregex;
    }

    public void setFilereplace(String filereplace) {
		if(filereplace == null) throw new NullPointerException();
        this.filereplace = filereplace;
    }

    public void setPackageregex(String packageregex) {
		if(packageregex == null) throw new NullPointerException();
        this.packageregex = packageregex;
    }

    public void setPackagereplace(String packagereplace) {
		if(packagereplace == null) throw new NullPointerException();
        this.packagereplace = packagereplace;
    }

    public void setDestdir(String destdir) {
		if(destdir == null) throw new NullPointerException();
        this.destdir = new File(destdir);
    }

    public File getDestdirFile() {
        return destdir;
    }

    public void setEncoding(String encoding) {
		if(encoding == null) throw new NullPointerException();
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getDestinationPackage(Object metadata) {
        String originalPackage = getMetadataProvider().getOriginalPackageName(metadata);
        return originalPackage.replaceAll(packageregex, packagereplace);
    }

    public String getDestinationFilename(Object metadata) {
        String originalFilename = getMetadataProvider().getOriginalFileName(metadata);
        String result = originalFilename.replaceAll(fileregex, filereplace);
        return result;
    }

    /**
     * Tests if an array is null or empty. (Helper method for templates).
     * @param array an array
     * @return true if it is null or empty
     */
    public boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Assert method. (Helper method for templates).
     * @param message failure message.
     * @param predicate should be true to avoid an exception.
     */
    public void assertTrue(String message, boolean predicate) {
        if(!predicate) {
            throw new RuntimeException(message);
        }
    }

    public Map getContextObjects() {
        return contextObjects;
    }

    public void start() {
		System.out.println("Running " + getClass().getName());
        try {
            Collection metadata = getMetadata();
            if (metadata == null) {
                throw new GeneramaException("Metadata was null. Got metadata from " + getMetadataProvider().toString(), null);
            }

            if (metadata.isEmpty()) {
                throw new GeneramaException("Metadata was empty. Got metadata from " + getMetadataProvider().toString(), null);
            }
            if (isMultioutput()) {
                for (Iterator iterator = metadata.iterator(); iterator.hasNext();) {
                    Object meta = (Object) iterator.next();
                    if (shouldGenerate(meta)) {
                        Writer out = getWriterMapper().getWriter(meta, this);
                        if(out != null) {
                            Map m = new HashMap();
                            m.put("metadata", meta);
                            populateContextMap(m);
                            templateEngine.generate(out, m, getEncoding(), getClass());
						}
                    }
                }
            } else {
                Writer out = getWriterMapper().getWriter("", this);
                if(out != null) {
                    Map m = new HashMap(contextObjects);
                    m.put("metadata", metadata);
                    populateContextMap(m);
	                templateEngine.generate(out, m, getEncoding(), getClass());
				}
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't generate content", e);
        }
    }

    /**
     * So that subclasses can choose what kind of metadata they want to use.
     * @return
     */
    protected Collection getMetadata() {
        return getMetadataProvider().getMetadata();
    }

    protected void populateContextMap(Map map) {
        map.put("plugin", this);
        map.put("dontedit", Plugin.DONTEDIT);
    }

    public void stop() {
    }

    protected WriterMapper getWriterMapper() {
        return writerMapper;
    }

    protected MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    public static String getPackageName(Class pluginClass) {
        String className = pluginClass.getName();
        String packageName = className.substring(0, className.lastIndexOf('.'));
        return packageName;
    }
}
