package org.generama.predicate;

import org.apache.commons.collections.Predicate;

import java.util.Collection;
import java.util.ArrayList;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class CompositePredicate implements Predicate {
    private Collection children = new ArrayList();

    protected Collection getChildren() {
        return children;
    }

    public void addPredicate(Predicate predicate) {
        children.add(predicate);
    }
}
