package org.generama.tests;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Baseclass for testing generation of XML content. Ignores
 * whitespace, ordering of attributes and comments.
 * Uses XMLUnit internally to compare equality of XML documents.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class AbstractXMLGeneratingPluginTestCase extends AbstractPluginTestCase implements EntityResolver {
    private Map dtds = new HashMap();
    protected DocumentBuilder expectedParser;
    protected DocumentBuilder actualParser;

    protected void registerDtd(String publicId, URL dtd) {
        dtds.put(publicId, dtd);

        // now turn on validation
        XMLUnit.getControlDocumentBuilderFactory().setValidating(true);
        XMLUnit.getTestDocumentBuilderFactory().setValidating(true);
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        URL dtd = (URL) dtds.get(publicId);
        if (dtd != null) {
            InputSource inputSource = new InputSource(dtd.openStream());
            inputSource.setPublicId(publicId);
            inputSource.setSystemId(systemId);
            return inputSource;
        }
        return null;
    }

    protected final void compare(URL expected, URL actual) throws IOException, SAXException {
        Document expectedDocument = XMLUnit.buildDocument(expectedParser, new InputSource(expected.openStream()));
        Document actualDocument = XMLUnit.buildDocument(actualParser, new InputSource(actual.openStream()));

        Diff diff = new Diff(expectedDocument, actualDocument) {
            public int differenceFound(Difference difference) {
                if ("sequence of attributes".equals(difference.getDescription())) {
                    return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
                }
                return super.differenceFound(difference);
            }
        };
        XMLTestCase xmlunit = new XMLTestCase(getName());

        xmlunit.assertXMLIdentical(diff, true);
    }

    protected void setUp() throws Exception {
        super.setUp();
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.getControlDocumentBuilderFactory().setIgnoringComments(true);
        XMLUnit.getTestDocumentBuilderFactory().setIgnoringComments(true);
        ErrorHandler errorHandler = new ErrorHandler() {
            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }
            public void warning(SAXParseException exception) {
            }
        };
        expectedParser = XMLUnit.getControlParser();
        expectedParser.setErrorHandler(errorHandler);
        expectedParser.setEntityResolver(this);

        actualParser = XMLUnit.getTestParser();
        actualParser.setErrorHandler(errorHandler);
        actualParser.setEntityResolver(this);
    }
}
