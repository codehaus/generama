package org.generama.tests;

import org.custommonkey.xmlunit.*;
import org.generama.OutputValidator;
import org.generama.defaults.XMLOutputValidator;
import org.w3c.dom.Document;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.net.URL;

/**
 * Baseclass for testing generation of XML content. Ignores
 * whitespace, ordering of attributes and comments.
 * Uses XMLUnit internally to compare equality of XML documents.
 *
 * @author Aslak Helles&oslash;y
 */
public abstract class AbstractXMLGeneratingPluginTestCase extends AbstractPluginTestCase {
    protected DocumentBuilder expectedParser;
    protected DocumentBuilder actualParser;

    protected final void compare(URL expected, URL actual) throws IOException, SAXException {
        Document expectedDocument = XMLUnit.buildDocument(expectedParser, new InputSource(expected.openStream()));
        Document actualDocument = XMLUnit.buildDocument(actualParser, new InputSource(actual.openStream()));

        Diff diff = new Diff(expectedDocument, actualDocument) {
            public int differenceFound(Difference difference) {
                if (difference == DifferenceConstants.CHILD_NODELIST_SEQUENCE ||
                        difference == DifferenceConstants.ATTR_SEQUENCE) {
                    return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
                }
/*
                if (difference == DifferenceConstants.DOCTYPE_NAME ||
                        difference == DifferenceConstants.DOCTYPE_PUBLIC_ID ||
                        difference == DifferenceConstants.DOCTYPE_SYSTEM_ID) {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                }
*/
                return super.differenceFound(difference);
            }
        };
        // go for anonym inner
        XMLTestCase xmlunit = new XMLTestCase(getName()){};

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

        actualParser = XMLUnit.getTestParser();
        actualParser.setErrorHandler(errorHandler);

        EntityResolver testResolver = null;

        //todo think do we need validation there??
        OutputValidator validator = plugin.getOutputValidator();
        if (validator != null) { //fixme isValidate condition should be removed, it is used to until plugin source
            if (!(validator instanceof XMLOutputValidator))
                throw new RuntimeException("For XMLGeneratedPlugin should be used XMLOutputValidator");

            XMLOutputValidator xmlOutputValidator = (XMLOutputValidator) validator;

            testResolver = xmlOutputValidator.getResolver();

            // now turn on validator
            if (plugin.isValidate()) {
                XMLUnit.getControlDocumentBuilderFactory().setValidating(true);
                XMLUnit.getTestDocumentBuilderFactory().setValidating(true);
            }
        } else {
            testResolver = new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return null;
                }
            };
        }

        expectedParser.setEntityResolver(testResolver);
        actualParser.setEntityResolver(testResolver);
    }
}
