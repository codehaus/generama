package org.generama.tests;

import org.generama.ant.AbstractGeneramaTask;
import org.generama.Generama;
import org.generama.MetadataProvider;
import org.generama.defaults.FileWriterMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.Arrays;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class DemoGeneramaTask extends AbstractGeneramaTask {
    public static class DemoMetadataProvider implements MetadataProvider {
        public Collection getMetadata() {
            return Arrays.asList(new String[] {"Hello", "World"});
        }

        public String getOriginalFileName(Object metadata) {
            return "";
        }

        public String getOriginalPackageName(Object metadata) {
            return "generama.demo";
        }

    }

    protected Generama createGenerama() {
        return new Generama(DemoMetadataProvider.class,FileWriterMapper.class);
    }
}
