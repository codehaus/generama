package org.generama.astunit;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;
import org.generama.astunit.parser.JavaLexer;
import org.generama.astunit.parser.JavaLexerEx;
import org.generama.astunit.parser.JavaRecognizer;
import org.generama.astunit.parser.TokenKeepingAST;

import java.io.IOException;
import java.net.URL;

/**
 * <p/>
 * ASTTestCase is a JUnit extension that compares two Java sources
 * (typically one kept as a static expected result and a generated one) on the AST level.
 * </p>
 * <p/>
 * Also see QDox' <a href="http://qdox.codehaus.org/apidocs/com/thoughtworks/qdox/junit/APITestCase.html">APITestCase</a> for comparing two sources on the API level.
 * </p>
 *
 * @author Aslak Helles&oslash;y (Original JavaCC version)
 * @author Laurent Etiemble (Port to ANTLR)
 */
public abstract class ASTTestCase extends TestCase {
    /**
     * Compares AST of both source readers.
     * <p/>
     * <p><b>Note:</b> This method is for backward naming compatiblity
     * with xjavadoc.codeunit.CodeTestCase.</p>
     *
     * @param expected the expected source
     * @param actual   the actual source
     */
    public static void assertAstEquals(URL expected, URL actual) throws RecognitionException, TokenStreamException, IOException {
        // Create a lexer that reads from the reader
        JavaLexer expectedLexer = new JavaLexerEx(expected.openStream());
        expectedLexer.setFilename(expected.toExternalForm());
        // Create a parser that reads from the scanner
        JavaRecognizer expectedParser = new JavaRecognizer(expectedLexer);
        expectedParser.setASTNodeClass(TokenKeepingAST.class.getName());

        // Create a lexer that reads from the reader
        JavaLexer actualLexer = new JavaLexerEx(actual.openStream());
        actualLexer.setFilename(actual.toExternalForm());
        // Create a parser that reads from the scanner
        JavaRecognizer actualParser = new JavaRecognizer(actualLexer);
        actualParser.setASTNodeClass(TokenKeepingAST.class.getName());

        // Parses the streams
        expectedParser.compilationUnit();
        actualParser.compilationUnit();

        // Gets the root ASTs
        TokenKeepingAST expectedRoot = (TokenKeepingAST) expectedParser.getAST();
        TokenKeepingAST actualRoot = (TokenKeepingAST) actualParser.getAST();

        // Test for equality
        assertAstEquals(expectedRoot, actualRoot);
    }

    /**
     * Generic method to compare recursively two AST nodes.
     * <p/>
     * There are three steps for the comparison :
     * <ul>
     * <li>- nodes are compared to each others</li>
     * <li>- first child nodes are compared to each others</li>
     * <li>- next sibling nodes are compared to each others</li>
     * </ul>
     * If all these comparison succeed, nodes are considered to be equals
     * </p>
     *
     * @param expected node
     * @param actual   node
     */
    private static void assertAstEquals(TokenKeepingAST expected, TokenKeepingAST actual) {
        assertEquals(expected, actual);

        if (expected != null) {
            TokenKeepingAST expectedChild = (TokenKeepingAST) expected.getFirstChild();
            TokenKeepingAST actualChild = (TokenKeepingAST) actual.getFirstChild();
            assertAstEquals(expectedChild, actualChild);

            TokenKeepingAST expectedSibling = (TokenKeepingAST) expected.getNextSibling();
            TokenKeepingAST actualSibling = (TokenKeepingAST) actual.getNextSibling();
            assertAstEquals(expectedSibling, actualSibling);
        }
    }
}
