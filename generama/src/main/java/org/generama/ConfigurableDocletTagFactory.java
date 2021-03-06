package org.generama;

import com.thoughtworks.qdox.model.DefaultDocletTag;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.DocletTagFactory;
import com.thoughtworks.qdox.model.AbstractBaseJavaEntity;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.JavaClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.net.URL;

/**
 * A paranoid tag factory that will remember
 * if it is asked to create a tag it doesn't know about. All the
 * standard javadoc tags (as of JDK 1.4.2) are preregistered.
 * It is possible to register a tag by class, so that tags with a
 * particular name will be of a particular class. It is also possible
 * to register tags simply by name, and they will be of the default type.
 * TODO:prevent output of multiple tag warning on the sameline
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class ConfigurableDocletTagFactory implements DocletTagFactory {
    private List unknownTags = new ArrayList();
    private final Map registeredTags = new HashMap();

    public ConfigurableDocletTagFactory() {
        String[] defaultTags = new String[]{"param", "author", "see", "since", "exception", "throws", "version", "return", "inheritDoc", "deprecated"};
        for (int i = 0; i < defaultTags.length; i++) {
            registerTag(defaultTags[i], DefaultDocletTag.class);
        }
    }

    public DocletTag createDocletTag(String tag, String text) {
        throw new UnsupportedOperationException();
    }

    public DocletTag createDocletTag(String tag, String text, AbstractBaseJavaEntity context, int lineNumber) {
        Class tagClass = (Class) registeredTags.get(tag);

        boolean isKnown = true;
        if (tagClass == null) {
            tagClass = DefaultDocletTag.class;
            isKnown = false;
        }
        try {
            Constructor newTag = tagClass.getConstructor(new Class[]{String.class, String.class, AbstractBaseJavaEntity.class, Integer.TYPE});
            DocletTag result = (DocletTag) newTag.newInstance(new Object[]{tag, text, context, new Integer(lineNumber)});

            if (!isKnown) {
                unknownTags.add(result);
            }
            return result;
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No (String, String, AbstractBaseJavaEntity, int) constructor in " + tagClass.getName());
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
    }

    public void registerTag(String tagName, Class tagClass) {
        registeredTags.put(tagName, tagClass != null ? tagClass : DefaultDocletTag.class);
    }

    public void registerTags(Map tags) {
        Set tagNames = tags.keySet();
        for (Iterator iterator = tagNames.iterator(); iterator.hasNext();) {
            String tagName = (String) iterator.next();
            registerTag(tagName, (Class) tags.get(tagName));
        }
    }

    public List getUnknownTags() {
        return unknownTags;
    }

    public void printUnknownTags() {
        for (Iterator iterator = unknownTags.iterator(); iterator.hasNext();) {
            DocletTag docletTag = (DocletTag) iterator.next();
            System.out.println("Unknown tag: @" + docletTag.getName() + " in " + getLocation(docletTag) + " (line " + docletTag.getLineNumber() + ")");
        }
    }

    public static String getLocation(DocletTag tag) {
        String location = null;
        final AbstractBaseJavaEntity tagCtx = tag.getContext();
        if (!(tagCtx instanceof AbstractJavaEntity)) {
            throw new IllegalArgumentException("Can't deduce location for " + tag + " in " + tagCtx);
        }
        URL sourceURL = ((AbstractJavaEntity)tagCtx).getSource().getURL();
        if (sourceURL != null) {
            location = sourceURL.toExternalForm();
        } else {
            // dunno what file it is (might be from a reader).
            JavaClass clazz;
            if (tag.getContext() instanceof JavaClass) {
                // it's on a class (outer class)
                clazz = (JavaClass) tag.getContext();
            } else {
                clazz = (JavaClass) tag.getContext().getParent();
            }
            location = clazz.getFullyQualifiedName();
        }
        return location;
    }
}
