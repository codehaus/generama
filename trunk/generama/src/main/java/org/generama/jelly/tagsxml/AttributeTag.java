package org.generama.jelly.tagsxml;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;

/**
 * Adds an XML attribute to the parent element tag like
 * the <code>&lt;xsl:attribute&gt;</code> tag.
 *
 * This class was copied from Apache Jelly-tags-xml.
 *
 * @author James Strachan
 * @version $Revision$
 */
public class AttributeTag extends TagSupport {
    /** The namespace URI. */
    private String namespace;

    /** the name of the attribute. */
    private String name;


    public AttributeTag() {
    }

    // Tag interface
    //-------------------------------------------------------------------------
    public void doTag(XMLOutput output) throws JellyTagException {
        ElementTag tag = (ElementTag) findAncestorWithClass(ElementTag.class);
        if (tag == null) {
            throw new JellyTagException(
                    "<attribute> tag must be enclosed inside an <element> tag");
        }
        tag.setAttributeValue(getName(), getBodyText(false), getURI());
    }

    // Properties
    //-------------------------------------------------------------------------

    /**
     * @return the name of the attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the attribute.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the namespace URI of the element
     */
    public String getURI() {
        return namespace;
    }

    /**
     * Sets the namespace URI of the element
     */
    public void setURI(String namespace) {
        this.namespace = namespace;
    }
}