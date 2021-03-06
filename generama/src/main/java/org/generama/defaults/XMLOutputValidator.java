package org.generama.defaults;

import org.dom4j.io.SAXReader;
import org.dom4j.DocumentException;
import org.generama.OutputValidationError;
import org.generama.OutputValidator;
import org.xml.sax.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.util.Map;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

/**
 * @author Anatol Pomozov
 */
public class XMLOutputValidator implements OutputValidator {
    private static final Log log = LogFactory.getLog(XMLOutputValidator.class);

    private EntityResolver resolver;

    public XMLOutputValidator() {
    }

    public XMLOutputValidator(EntityResolver resolver) {
        this.resolver = resolver;
    }

    public XMLOutputValidator(Map dtds) {
        this.resolver = new MapPairsEntityResolver(dtds);
    }

    public XMLOutputValidator(File basedir, String prefix) {
        this.resolver = new DirEntityResolver(basedir, prefix);
    }

    public XMLOutputValidator(URL basedir, String prefix) {
        this.resolver = new DirEntityResolver(basedir, prefix);
    }

    public EntityResolver getResolver() {
        return resolver;
    }

    public void validate(URL url) throws OutputValidationError {
        if (log.isDebugEnabled()) {
            log.debug("Validating " + url.getFile() + "... ");
        }

        SAXReader reader = new SAXReader();
        // SOURCE: Validation of Schemas: http://www.burnthacker.com/archives/000086.html
        try {
			// Enables validation in general
			reader.setFeature("http://xml.org/sax/features/validation", true);
            // Enables XML Schema Validation
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            // Enables full (if slow) schema checking
            reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
            // Only validate if there is enough data in the document to validate. This is important if you don't have DTD or Schema data in everything you parse.
            // reader.setFeature("http://apache.org/xml/features/validation/dynamic", true);
		} catch (SAXException e) {
            log.error("SAXException", e);
		}
        // END
        reader.setValidation(true);
        reader.setEntityResolver(resolver);

        final ArrayList exceptions = new ArrayList();
        reader.setErrorHandler(new ErrorHandler() {
            public void error(SAXParseException exception) throws SAXException {
                exceptions.add(exception);
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                exceptions.add(exception);
            }

            public void warning(SAXParseException exception) throws SAXException {
            }
        });

        try {
            reader.read(url.openStream());
        } catch (DocumentException e) {
            throw new OutputValidationError("ParsingFailed " + e.getMessage(), url);
        } catch (IOException e) {
            log.error("IOExcepton", e);
        }

        if (!exceptions.isEmpty()) {
            SAXParseException e = (SAXParseException) exceptions.get(0);
            String message = "\n   Line: " + e.getLineNumber() + " Column: " + e.getColumnNumber() + "\n" +
                    "   Message: " + e.getMessage();
            throw new OutputValidationError(message, url);
        }
    }
}
