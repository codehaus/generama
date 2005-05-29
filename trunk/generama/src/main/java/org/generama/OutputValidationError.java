package org.generama;

import java.net.URL;

/**
 * @author Anatol Pomozov
 */
public class OutputValidationError extends Exception {
    private URL url;

    public OutputValidationError(String message, URL url) {
        super(message);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}
