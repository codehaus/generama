package org.generama.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.net.URL;

/**
 * abstract test case for plugins generating property files
 * @author Konstantin Pribluda
 */

public abstract class AbstractPropertyGeneratingPluginTestCase extends AbstractTextGeneratingPluginTestCase {

    protected void compare(URL expected, URL actual) throws IOException {
        Properties ex = new Properties();
        ex.load(new ByteArrayInputStream(read(expected).getBytes()));
        Properties ac = new Properties();
        ac.load(new ByteArrayInputStream(read(actual).getBytes()));

        assertEquals("Properties should be equal", ex, ac);
    }
}

