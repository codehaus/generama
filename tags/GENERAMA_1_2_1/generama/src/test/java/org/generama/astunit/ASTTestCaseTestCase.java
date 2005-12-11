package org.generama.astunit;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.AssertionFailedError;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * test of the test 8-)
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class ASTTestCaseTestCase extends ASTTestCase {
    private String basedir;
    private File lineAndColumnKeepingASTSource;

    protected void setUp() {
        basedir = System.getProperty("generama.home");
        assertNotNull("generama.home sys prop should point to home dir of generama", basedir);
        lineAndColumnKeepingASTSource = new File(basedir + "/src/main/java/org/generama/astunit/parser/TokenKeepingAST.java");
    }

    public void testASourceFileIsEqualToItself() throws IOException, RecognitionException, TokenStreamException, MalformedURLException {
        assertAstEquals(lineAndColumnKeepingASTSource.toURL(), lineAndColumnKeepingASTSource.toURL());
    }

    public void testDifferentSourceFilesReportLineNumberOfFirstDifference() throws IOException, RecognitionException, TokenStreamException, MalformedURLException {
        File notQuiteLineAndColumnKeepingASTSource = new File(basedir + "/src/astunittestdata/NotQuiteTokenKeepingAST.java");
        try {
            assertAstEquals(lineAndColumnKeepingASTSource.toURL(), notQuiteLineAndColumnKeepingASTSource.toURL());
            fail();
        } catch (AssertionFailedError e) {
			// TODO: we should assert something about the message here.
        }
    }
}
