package org.generama.jelly;

import com.thoughtworks.qdox.model.DefaultDocletTag;
import junit.framework.TestCase;
import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.parser.XMLParser;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Test cases for the ConditionalElementTag (jelly tag).
 * This doesn't use the jelly testing framework but rather makes simpler unit
 * tests that don't require writing the tests within a jelly script.
 * If other jelly tags were to be test, this could easily be refactored into some
 * abstract TestCase class from which other tests could extend.
 *
 *
 * @author Gr&eacute;gory Joseph(moins_moins)
 * @author $Author$ (last edit)
 * @version $Revision$
 */
public class ConditionalElementTagTestCase extends TestCase {
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private static final String XML_SCRIPTPREFIX = "<j:jelly xmlns:j=\"jelly:core\" xmlns:x=\"jelly:org.generama.jelly.GeneramaTaglib\">\n\n";
    private static final String XML_SCRIPTSUFFIX = "</j:jelly>";

    private JellyContext jellyCtx;

    protected void setUp() throws Exception {
        super.setUp();
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.getControlDocumentBuilderFactory().setIgnoringComments(true);
        XMLUnit.getTestDocumentBuilderFactory().setIgnoringComments(true);

        jellyCtx = new JellyContext();
        jellyCtx.registerTagLibrary("x", new GeneramaTaglib());
        // prepareDefaultSauce():
        Mayonnaise mayo = new Mayonnaise();
        mayo.setEggCount(2);
        mayo.setBrandName("D+L");
        mayo.setEatable(true); //and it's damn good
        jellyCtx.setVariable("mayo", mayo);
    }

    public void testSimpleObj() throws Exception {
        // test de-hyphenation
        String scriptStr = "<x:condelement tag=\"oeufs\" obj=\"${mayo}\" property=\"egg-count\"/>";
        checkScriptOutput(scriptStr, "<oeufs>2</oeufs>");

        // test that passing an already de-hyphenated property name also works
        scriptStr = "<x:condelement tag=\"mayo\" obj=\"${mayo}\" property=\"brandName\"/>";
        checkScriptOutput(scriptStr, "<mayo>D+L</mayo>");

        // the name catch (using an Object it should call the getName() method
        scriptStr = "<x:condelement tag=\"mayo\" obj=\"${mayo}\" property=\"name\"/>";
        checkScriptOutput(scriptStr, "<mayo>mayoName</mayo>");
    }

    public void testDocletTag() throws Exception {
        // test de-hyphenation
        String scriptStr = "<x:condelement tag=\"oeufs\" doclettag=\"${mayo}\" property=\"egg-count\"/>";
        checkScriptOutput(scriptStr, "<oeufs>2</oeufs>");

        // check boolean
        scriptStr = "<x:condelement tag=\"yummy\" doclettag=\"${mayo}\" property=\"eatable\"/>";
        checkScriptOutput(scriptStr, "<yummy>true</yummy>");

        // the name catch (using a DocletTag it should call the getName_() method
        scriptStr = "<x:condelement tag=\"mayo\" doclettag=\"${mayo}\" property=\"name\"/>";
        checkScriptOutput(scriptStr, "<mayo>Mayonnaise D+L</mayo>");
    }

    protected void checkScriptOutput(String scriptStr, String expectedResult)
            throws Exception, IOException, JellyException, ParserConfigurationException {
        StringWriter sw = new StringWriter();
        OutputFormat format = OutputFormat.createCompactFormat();
        final XMLWriter xmlWriter = new XMLWriter(sw, format);
        XMLOutput xmlOutput = new XMLOutput() {
            public void close()
                    throws IOException {
                xmlWriter.close();
            }
        };
        xmlOutput.setContentHandler(xmlWriter);
        xmlOutput.setLexicalHandler(xmlWriter);

        XMLParser parser = new XMLParser();
        parser.setContext(jellyCtx);

        Reader reader = new StringReader(XML_HEADER + XML_SCRIPTPREFIX + scriptStr + XML_SCRIPTSUFFIX);
        Script script = parser.parse(reader);

        xmlOutput.startDocument();
        script.run(jellyCtx, xmlOutput);
        xmlOutput.endDocument();
        xmlOutput.flush();

        Diff diff = new Diff(expectedResult, sw.toString());
        XMLTestCase xmlunit = new XMLTestCase(this.getName());
        xmlunit.assertXMLIdentical(diff, true);
    }

    public class Mayonnaise extends DefaultDocletTag {
        private int eggCount;
        private String brandName;
        private boolean eatable;

        public Mayonnaise() {
            super("mayoName", "mayoValue");
        }

        // here's the catch - this method should be called when we use a DocletTag, while the other when we use it an Object
        public String getName_() {
            return "Mayonnaise " + this.brandName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public boolean isEatable() {
            return eatable;
        }

        public void setEatable(boolean eatable) {
            this.eatable = eatable;
        }

        public int getEggCount() {
            return eggCount;
        }

        public void setEggCount(int eggCount) {
            this.eggCount = eggCount;
        }
    }
}
