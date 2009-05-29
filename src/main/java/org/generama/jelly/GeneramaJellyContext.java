package org.generama.jelly;

import org.apache.commons.jelly.JellyContext;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * A JellyContext that looks for scripts on the classpath.
 * This makes it possible to look outside the plugin. Relative
 * paths is not supported, so this behaves like Velocity.
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class GeneramaJellyContext extends JellyContext {
    public GeneramaJellyContext(JellyContext jellyContext, URL url) {
        super(jellyContext, url);
    }

    public GeneramaJellyContext(URL url, URL url1) {
        super(url, url1);
    }

    public GeneramaJellyContext(JellyContext jellyContext, URL url, URL url1) {
        super(jellyContext, url, url1);
    }

    public GeneramaJellyContext(URL url) {
        super(url);
    }

    public GeneramaJellyContext() {
        super();
    }

    public GeneramaJellyContext(JellyContext jellyContext) {
        super(jellyContext);
    }

    public URL getResource(String uri) throws MalformedURLException {
        URL resource = getClass().getClassLoader().getResource(uri);
        if (resource != null)
            return resource;

        return super.getResource(uri);
    }

    protected JellyContext createChildContext() {
        return new GeneramaJellyContext(this);
    }
}
