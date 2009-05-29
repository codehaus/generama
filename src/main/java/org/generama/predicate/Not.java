package org.generama.predicate;

import org.apache.commons.collections.Predicate;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class Not extends CompositePredicate {
    public void addPredicate(Predicate predicate) {
        if (getChildren().size() >= 1) {
            throw new IllegalStateException("Only one sub-predicate allowed for not");
        }
        super.addPredicate(predicate);
    }

    public boolean evaluate(Object o) {
        if (getChildren().size() != 1) {
            throw new IllegalStateException("Not requires one sub-predicate");
        }
        return !((Predicate) getChildren().iterator().next()).evaluate(o);
    }
}

