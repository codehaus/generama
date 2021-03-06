package org.generama.defaults;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.generama.Plugin;
import org.generama.WriterMapper;

/**
 * @author Aslak Helles&oslash;y
 */
public class FileWriterMapper implements WriterMapper {
    public Outcome getOutcome(Object metadata, Plugin plugin) throws IOException {
        String pakkage = plugin.getDestinationPackage(metadata);
        String packagePath = pakkage.replace('.', '/');
        File dir = new File(plugin.getDestdirFile(), packagePath);
        dir.mkdirs();

        String filename = plugin.getDestinationFilename(metadata);
        File out = new File(dir, filename);
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(out), plugin.getEncoding());
            return new Outcome(writer, out.toURL());
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e.toString());
        }
    }
}

