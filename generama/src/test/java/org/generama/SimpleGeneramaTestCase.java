package org.generama;

import org.generama.tests.SinkWriterMapper;
import junit.framework.TestCase;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class SimpleGeneramaTestCase extends TestCase {

    public void testDummy() {}

    protected Generama createGeneramaWithThreeMetadataObjects() {
        Generama generama = new Generama(OneTwoThreeStringMetadataProvider.class,
                SinkWriterMapper.class);
        return generama;
    }
}
