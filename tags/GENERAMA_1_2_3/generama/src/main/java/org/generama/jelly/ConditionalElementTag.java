package org.generama.jelly;

import com.thoughtworks.qdox.model.DocletTag;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;
import org.xml.sax.SAXException;

import java.lang.reflect.InvocationTargetException;

/**
 * This Jelly tag outputs a property value from any bean object within a given
 * xml tag, if that property is not null.
 * If the passed object is a DocletTag implementation from the qtag plugin,
 * then the result will be equivalent to doclettag.getNamedParameter(property),
 * if that tag's property was set.
 *
 *
 * <p><br/><br/>created     Oct 21, 2003 11:36:07 PM</p>
 * @author     Gr�gory Joseph(moins_moins)
 * @author     $Author$ (last edit)
 * @version    $Revision$
 */
public class ConditionalElementTag extends TagSupport {
    private String xmlTag;
    private DocletTag docletTag;
    private Object obj;
    private String propertyName;

    public void doTag(XMLOutput xmlOutput) throws MissingAttributeException {
        if (xmlTag == null) {
            throw new MissingAttributeException("tag");
        } else if (obj == null && docletTag == null) {
            throw new MissingAttributeException("obj or doclettag");
        } else if (obj != null && docletTag != null) {
            throw new RuntimeException("You can't define both obj and doclettag in this tag.");
        } else if (propertyName == null) {
            throw new MissingAttributeException("property");
        }

        try {
            String value = null;
            try {
                if (obj != null) {
                    propertyName = deHyphenate(propertyName, false);
                } else {
                    propertyName = deHyphenate(propertyName, true);
                    obj = docletTag;
                }
                value = BeanUtils.getSimpleProperty(obj, propertyName);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }

            if (value != null) {
                xmlOutput.startElement(xmlTag);
                xmlOutput.write(value);
                xmlOutput.endElement(xmlTag);
            }
        } catch (SAXException ex) {
            throw new RuntimeException("Couldn't do output: " + ex.getMessage(), ex);
        } finally {
            xmlTag = null;
            docletTag = null;
            obj = null;
            propertyName = null;
        }
    }

    /**
     * Sets the xml tag to be used.
     * @param xmlTag
     */
    public void setTag(String xmlTag) {
        this.xmlTag = xmlTag;
    }

    /**
     * Set the object from which the property should be read.
     * @param object
     */
    public void setObj(Object object) {
        this.obj = object;
    }

    /**
     * Set the DocletTag from which the property should be read.
     * This property exists to make it more clear in a jelly script that
     * the passed object is a DocletTag, but also because the de-hyphenation
     * for the property name will add a _ at the end of the property name
     * in case we use a DocletTag.
     *
     * @param tag
     */
    public void setDoclettag(DocletTag tag) {
        this.docletTag = tag;
    }

    /**
     * Sets the property name whose value will be taken from the doclettag and
     * used as content of the tag.
     *
     * @param property
     */
    public void setProperty(String property) {
        this.propertyName = property;
    }

    /**
     * Inspired by QTagUtils's hyphenate method, this one does the opposite.
     *
     * @param hyphenatedName
     * @return
     */
    private String deHyphenate(String hyphenatedName, boolean isDocletTag) {
        StringBuffer sb = new StringBuffer();
        //sb.append(Character.toLowerCase(camelCase.charAt(0)));
        char[] chars = hyphenatedName.toCharArray();
        //char separator = firstIsDot ? '.' : '-';
        boolean capitalizeNext = false;
        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];
            if (currentChar == '-' || currentChar == '.') {
                capitalizeNext = true;
            } else if (capitalizeNext == true) {
                currentChar = Character.toUpperCase(currentChar);
                sb.append(currentChar);
                capitalizeNext = false;
            } else {
                sb.append(currentChar);
            }
        }

        // DocletTag implementations will have a _ appended to their getName and getValue methods if an property is called name or value
        if (isDocletTag && (hyphenatedName.equals("name") || hyphenatedName.equals("class") || hyphenatedName.equals("value"))) {
            sb.append('_');
        }

        return sb.toString();
    }
}
