package org.generama.tests;

import org.generama.WriterMapper;
import org.generama.Plugin;

import java.io.Writer;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class SinkWriterMapper implements WriterMapper {
    private StringWriter sink = new StringWriter();

    public Writer getWriter(Object metadata, Plugin plugin) throws IOException {
        return sink;
    }

    public String getContent() {
        return sink.getBuffer().toString();
    }
}
