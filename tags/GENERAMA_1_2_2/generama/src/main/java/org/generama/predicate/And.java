package org.generama.predicate;

import org.apache.commons.collections.Predicate;

import java.util.Iterator;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class And extends CompositePredicate {

    public boolean evaluate(Object o) {
        for (Iterator children = getChildren().iterator(); children.hasNext();) {
            Predicate child = (Predicate) children.next();

            if (!child.evaluate(o)) {
                return false;
            }
        }

        return true;
    }

}
