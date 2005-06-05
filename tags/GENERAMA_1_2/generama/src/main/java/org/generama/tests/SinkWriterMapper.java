package org.generama.tests;

import org.generama.Plugin;
import org.generama.WriterMapper;
import org.generama.defaults.Outcome;

import java.io.*;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLConnection;

/**
 * @author Aslak Helles&oslash;y
 */
public class SinkWriterMapper implements WriterMapper {
    private StringWriter writer = new StringWriter();

    public Outcome getOutcome(Object metadata, Plugin plugin) throws IOException {
        URL url = new URL("string", "", -1, "sinkWriter", new URLStreamHandler(){
            protected URLConnection openConnection(URL u) {
                return new URLConnection(u) {
                    public void connect() {
                    }

                    public InputStream getInputStream() {
                        return new StringBufferInputStream(SinkWriterMapper.this.getContent());
                    }
                };
            }
        });

        return new Outcome(writer, url);
    }

    public String getContent() {
        return writer.getBuffer().toString();
    }
}
