package org.generama.jelly.tagsxml;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Replace namespace is a filter to change the namespace of any
 * elemement attribute passing through it.
 *
 * This class was copied from Apache Jelly-tags-xml.
 *
 * @author Diogo Quintela <dquintela@gmail.com>
 */
public class ReplaceNamespaceTag extends TagSupport {
    private String fromNamespace;
    private String toNamespace;

    public ReplaceNamespaceTag() {
    }

    //  Tag interface
    //-------------------------------------------------------------------------
    public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
        final String fromURI = (fromNamespace != null) ? fromNamespace : "";
        final String toURI = (toNamespace != null) ? toNamespace : "";
        XMLOutput newOutput = output;

        if (!toURI.equals(fromURI)) {
            newOutput = new XMLOutput(output) {
                public void startElement(String uri, String localName, String qName, Attributes atts)
                        throws SAXException {
                    super.startElement(replaceURI(uri), localName, qName, replaceURI(atts));
                }

                public void endElement(String uri, String localName, String qName)
                        throws SAXException {
                    super.endElement(replaceURI(uri), localName, qName);
                }

                public void startPrefixMapping(String prefix, String uri)
                        throws SAXException {
                    super.startPrefixMapping(prefix, replaceURI(uri));
                }

                private String replaceURI(String uri) {
                    String newUri = uri;

                    if (fromURI.equals((uri != null) ? uri : "")) {
                        newUri = toURI;
                    }

                    return newUri;
                }

                private Attributes replaceURI(Attributes atts) {
                    AttributesImpl newAttsImpl = new AttributesImpl();

                    for (int i = 0; i < atts.getLength(); i++) {
                        // Normally attributes don't have namespaces
                        // But may have (only if on form prefix:attr) ?
                        // So, we'll only replace if needed
                        String QName = atts.getQName(i);
                        String newUri = atts.getURI(i);
                        int idx = QName.indexOf(':');

                        if (idx >= 0) {
                            newUri = replaceURI(newUri);
                        }

                        newAttsImpl.addAttribute(newUri, atts.getLocalName(i), atts.getQName(i),
                                atts.getType(i), atts.getValue(i));
                    }

                    return newAttsImpl;
                }
            };
        }

        invokeBody(newOutput);
    }

    /**
     * @return the source namespace URI to replace
     */
    public String getFromURI() {
        return fromNamespace;
    }

    /**
     * Sets the source namespace URI to replace.
     */
    public void setFromURI(String namespace) {
        this.fromNamespace = namespace;
    }

    /**
     * @return the destination namespace URI to replace
     */
    public String getToURI() {
        return toNamespace;
    }

    /**
     * Sets the destination namespace URI to replace.
     */
    public void setToURI(String namespace) {
        this.toNamespace = namespace;
    }
}