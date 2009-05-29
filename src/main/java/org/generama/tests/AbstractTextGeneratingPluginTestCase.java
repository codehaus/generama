package org.generama.tests;

import java.io.Reader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Baseclass for testing generation of Java sources.
 * Compares content by comparing the Strings (and is therefore
 * sensitive to whitespaces too.
 *  
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class AbstractTextGeneratingPluginTestCase extends AbstractPluginTestCase {

    protected void compare(URL expected, URL actual) throws IOException {
        String ex = read(expected);
        String ac = read(actual);
        assertEquals("Text should be equal", ex, ac);
    }

    protected String read(URL url) throws IOException {
        Reader reader = new InputStreamReader(url.openStream());
        BufferedReader br = new BufferedReader(reader);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String line;
        while((line = br.readLine()) != null) {
            pw.println(line);
        }
        pw.flush();
        return sw.getBuffer().toString();
    }
}
