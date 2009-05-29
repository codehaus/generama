package org.generama.jelly;

import org.apache.commons.jelly.TagLibrary;
import org.generama.jelly.tagsxml.ElementTag;
import org.generama.jelly.tagsxml.AttributeTag;
import org.generama.jelly.tagsxml.ReplaceNamespaceTag;

/**
 *
 * <p><br/><br/>created     Oct 21, 2003 11:34:52 PM</p>
 * @author     Gr&eacute;gory Joseph
 * @author     $Author$ (last edit)
 * @version    $Revision$
 */
public class GeneramaTaglib extends TagLibrary {
    public GeneramaTaglib() {
        super();
        registerTag("condelement", ConditionalElementTag.class);
        registerTag("merge", MergeTag.class);

        // These were copied from Apache Jelly-tags-xml.
        registerTag("element", ElementTag.class);
        registerTag("attribute", AttributeTag.class);
        registerTag("replaceNamespace", ReplaceNamespaceTag.class);
    }
}
