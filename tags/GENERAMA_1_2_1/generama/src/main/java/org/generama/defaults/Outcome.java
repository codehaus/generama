package org.generama.defaults;

import java.io.Writer;
import java.net.URL;

/**
 * @author Anatol Pomozov
 */
public class Outcome {
    private Writer writer;
    private URL url;

    public Outcome(Writer writer, URL url) {
        this.writer = writer;
        this.url = url;
    }

    public Writer getWriter() {
        return writer;
    }

    public URL getURL() {
        return url;
    }
}
