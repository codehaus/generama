package org.generama.defaults;

import org.generama.Plugin;
import org.generama.WriterMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FileWriterMapper implements WriterMapper {
    public Writer getWriter(Object metadata, Plugin plugin) throws IOException {
        String pakkage = plugin.getDestinationPackage(metadata);
        String packagePath = pakkage.replace('.', '/');
        File dir = new File(plugin.getDestdirFile(), packagePath);
        dir.mkdirs();

        String filename = plugin.getDestinationFilename(metadata);
        File out = new File(dir, filename);
        try {
            return new OutputStreamWriter(new FileOutputStream(out), plugin.getEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e.toString());
        }
    }
}

