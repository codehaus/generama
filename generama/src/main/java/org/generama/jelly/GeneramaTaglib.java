package org.generama.jelly;

import org.apache.commons.jelly.TagLibrary;

/**
 *
 *
 *
 * <p><br/><br/>created     Oct 21, 2003 11:34:52 PM</p>
 * @author     Gr&eacute;gory Joseph(moins_moins)
 * @author     $Author$ (last edit)
 * @version    $Revision$
 */
public class GeneramaTaglib extends TagLibrary {
    public GeneramaTaglib() {
        super();
        registerTag("condelement", ConditionalElementTag.class);
        registerTag("merge", MergeTag.class);
    }
}
