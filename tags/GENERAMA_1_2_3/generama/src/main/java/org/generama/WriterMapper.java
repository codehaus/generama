package org.generama;

import org.generama.defaults.Outcome;

import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 */
public interface WriterMapper {
    Outcome getOutcome(Object metadata, Plugin plugin) throws IOException;
}
