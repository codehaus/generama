package org.generama;

import java.io.Writer;
import java.util.Map;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface TemplateEngine {
    void generate(Writer out, Map contextObjects, String encoding, Class pluginClass) throws GeneramaException;
}