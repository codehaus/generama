package org.generama;

import org.generama.defaults.StringWriterMapper;
import junit.framework.TestCase;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class SimpleGeneramaTestCase extends TestCase /*AbstractGeneramaTestCase*/ {

    public void testDummy() {}

    protected Generama createGeneramaWithThreeMetadataObjects() {
        Generama generama = new Generama(OneTwoThreeStringMetadataProvider.class,
                StringWriterMapper.class);
        return generama;
    }
}
