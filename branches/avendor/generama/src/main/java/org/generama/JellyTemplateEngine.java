package org.generama;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.XMLOutput;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXContentHandler;
import org.dom4j.io.XMLWriter;
import org.generama.jelly.GeneramaJellyContext;
import org.xml.sax.SAXException;

import java.io.Writer;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JellyTemplateEngine extends AbstractTemplateEngine implements TemplateEngine {
    private boolean supressdeclaration = false;
    private boolean expandEmpty = false;

    public final void generate(Writer out, Map contextObjects, String encoding, Class pluginClass) throws GeneramaException {
        JellyContext context = new GeneramaJellyContext();
        Set keys = contextObjects.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = contextObjects.get(key);
            context.setVariable(key, value);
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(encoding);
        format.setSuppressDeclaration(supressdeclaration);
        format.setExpandEmptyElements(expandEmpty);
        final XMLWriter xmlWriter = new XMLWriter(out, format);
        xmlWriter.setEscapeText(false);

        // Create the output
        XMLOutput xmlOutput = new XMLOutput();

        SAXContentHandler saxHandler = new SAXContentHandler();

        xmlOutput.setContentHandler(saxHandler);
        xmlOutput.setLexicalHandler(saxHandler);

        try {
            xmlOutput.startDocument();
            context.runScript(getScriptURL(pluginClass, ".jelly"), xmlOutput);
            xmlOutput.endDocument();
            xmlWriter.write(saxHandler.getDocument());
            xmlWriter.flush();
            xmlWriter.close();
        } catch (SAXException e) {
            throw new GeneramaException("Exception occurred when running Jelly", e);
        } catch (JellyException e) {
            throw new GeneramaException("Exception occurred when running Jelly", e);
        } catch (Exception e) {
            throw new GeneramaException("Exception occurred when running Jelly", e);
        }
    }

    /**
     * Set to true to supress XML declaration in generated output.
     *
     * @param supressdeclaration
     */
    public void setSupressdeclaration(boolean supressdeclaration) {
        this.supressdeclaration = supressdeclaration;
    }

    /**
     * Set to true if you like empty elements expanded
     *
     * @param expandEmpty
     */
    public void setExpandEmpty(boolean expandEmpty) {
        this.expandEmpty = expandEmpty;
    }
}
