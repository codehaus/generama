package org.generama.defaults;

import org.generama.Plugin;
import org.generama.WriterMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class StringWriterMapper implements WriterMapper {
    public Writer getWriter(Object metadata, Plugin plugin) throws IOException {
        return new StringWriter();
    }
}
