package org.generama.ant;

import org.generama.Generama;
import org.nanocontainer.ant.PicoContainerTask;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class AbstractGeneramaTask extends PicoContainerTask  {

    protected AbstractGeneramaTask() {
        extraContainerComposer = createGenerama();
    }

    protected abstract Generama createGenerama();
}
