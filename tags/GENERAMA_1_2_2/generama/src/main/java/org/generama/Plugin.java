package org.generama;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.generama.defaults.Outcome;
import org.picocontainer.Startable;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class Plugin implements Startable {
    protected static final String DONTEDIT = "Generated file. Do not edit.";
    protected WriterMapper writerMapper;
    protected MetadataProvider metadataProvider;
    private File destdir;
    private String packageregex = "";
    private String packagereplace = "";
    private String fileregex = "";
    private String filereplace = "";
    private String encoding = "ISO-8859-1";
    private boolean isMultioutput = false;
    private final TemplateEngine templateEngine;
    private Map contextObjects = new HashMap();
    private OutputValidator outputValidator;
    private boolean validate = true;

    public Plugin(TemplateEngine templateEngine, MetadataProvider metadataProvider, WriterMapper writerMapper) {
        this.templateEngine = templateEngine;
        this.metadataProvider = metadataProvider;
        this.writerMapper = writerMapper;
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

    public void setOutputValidator(OutputValidator outputValidator) {
        this.outputValidator = outputValidator;
    }

    public OutputValidator getOutputValidator() {
        return outputValidator;
    }

    /**
     * @param fileregex a regular expression indicating what parts of each metadata object's file name
     * should be replaced in the output file.
     */
    public void setFileregex(String fileregex) {
        if (fileregex == null) throw new NullPointerException();
        this.fileregex = fileregex;
    }

    public void setFilereplace(String filereplace) {
        if (filereplace == null) throw new NullPointerException();
        this.filereplace = filereplace;
    }

    public void setPackageregex(String packageregex) {
        if (packageregex == null) throw new NullPointerException();
        this.packageregex = packageregex;
    }

    public void setPackagereplace(String packagereplace) {
        if (packagereplace == null) throw new NullPointerException();
        this.packagereplace = packagereplace;
    }

    /**
     * Directory where generated file should be created
     *
     * @generama.property required="true"
     */
    public void setDestdir(String destdir) {
        if (destdir == null) throw new NullPointerException();
        this.destdir = new File(destdir);
    }

    public File getDestdirFile() {
        return destdir;
    }

    /**
     * Validate output files or not.
     *
     * @generama.property required="false" default="true"
     */
    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public boolean isValidate() {
        return validate;
    }

    /**
     * Encoding of source files
     *
     * @generama.property required="false" default="ISO-8859-1"
     */
    public void setEncoding(String encoding) {
        if (encoding == null) throw new NullPointerException();
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getDestinationPackage(Object metadata) {
        String originalPackage = metadataProvider.getOriginalPackageName(metadata);
        if(originalPackage == null) {
            originalPackage="";
        }
        return originalPackage.replaceAll(packageregex, packagereplace);
    }

    public String getDestinationFilename(Object metadata) {
        String originalFilename = metadataProvider.getOriginalFileName(metadata);
        String result = originalFilename.replaceAll(fileregex, filereplace);
        return result;
    }

    /**
     * Tests if an array is null or empty. (Helper method for templates).
     *
     * @param array an array
     * @return true if it is null or empty
     */
    public boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Returns true if the given file is in the given directory or one of its subdirectories.
     * More specifically returns true is the file's canonical path starts with the
     * directory's canonical path. (Helper method)
     */
    protected boolean isIn(File file, File directory) {
        try {
            final String filePath = file.getCanonicalPath();
            final String dirPath = directory.getCanonicalPath();
            return filePath.startsWith(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see #isIn(java.io.File, java.io.File)
     */
    protected boolean isIn(String file, String directory) {
        return isIn(new File(file), new File(directory));
    }

    /**
     * Assert method. (Helper method for templates).
     *
     * @param message   failure message.
     * @param predicate should be true to avoid an exception.
     */
    public void assertTrue(String message, boolean predicate) {
        if (!predicate) {
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
                throw new GeneramaException("Metadata was null. Got metadata from " + metadataProvider.toString(), null);
            }

            if (metadata.isEmpty()) {
                System.err.println("Metadata was empty. Got metadata from " + metadataProvider.toString());
                return;
            }
            if (isMultioutput()) {
                for (Iterator iterator = metadata.iterator(); iterator.hasNext();) {
                    Object meta = iterator.next();
                    if (shouldGenerate(meta)) {
                        Outcome out = getWriterMapper().getOutcome(meta, this);
                        if (out.getWriter() != null) {
                            Map m = new HashMap();
                            m.put("metadata", meta);
                            populateContextMap(m);
                            preGenerate();
                            templateEngine.generate(out.getWriter(), m, getEncoding(), getClass());

                            if (validate && outputValidator != null) {
                                outputValidator.validate(out.getURL());
                            }
                            out.getWriter().close();
                        }
                    }
                }
            } else {
                Outcome out = getWriterMapper().getOutcome("", this);
                if (out.getWriter() != null) {
                    Map m = new HashMap(contextObjects);
                    Collection filtered = CollectionUtils.select(metadata, new Predicate() {
                        public boolean evaluate(Object o) {
                            return shouldGenerate(o);
                        }
                    });
                    m.put("metadata", filtered);
                    populateContextMap(m);
                    preGenerate();
                    templateEngine.generate(out.getWriter(), m, getEncoding(), getClass());

                    if (validate && outputValidator != null) {
                        outputValidator.validate(out.getURL());
                    }
                    out.getWriter().close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't generate content", e);
        } catch (OutputValidationError e) {
            System.err.println("File " + e.getUrl().getFile() + " did not pass validation: " + e.getMessage());
            throw e;
        }
    }

    /**
     * So that subclasses can choose what kind of metadata they want to use.
     */
    abstract protected Collection getMetadata();

    protected void populateContextMap(Map map) {
        map.put("class", map.get("metadata"));
        map.put("plugin", this);
        map.put("dontedit", Plugin.DONTEDIT);
    }
    
    /**
     * A method called right before generation is called (one time if multiOutput is false, several otherwise).
     * Allows for late setting of directorios for example in MergeableVelocityTemplateEngine
     *
     * @see MergeableVelocityTemplateEngine#addPath(String)
     */
    protected void preGenerate() {
        // Default impl is empty
    }
    public void stop() {
//    	 Default impl is empty
    }

    protected WriterMapper getWriterMapper() {
        return writerMapper;
    }

    public static String getPackageName(Class pluginClass) {
        String className = pluginClass.getName();
        String packageName = className.substring(0, className.lastIndexOf('.'));
        return packageName;
    }
}