package org.generama;

import java.io.Writer;
import java.io.IOException;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface WriterMapper {
    Writer getWriter(Object metadata, Plugin plugin) throws IOException;
}
