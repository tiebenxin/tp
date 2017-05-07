package com.example.haoyuban111.mubanapplication.files;

import com.example.haoyuban111.mubanapplication.log.LogWriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileWriter implements IFileWriter {

    public static final FileWriter DEFAULT = new FileWriter();

    @Override
    public boolean write(InputStream stream, File file) {
        return writeFile(stream, file);
    }

    public static boolean writeFile(InputStream in, File file) {
        final File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        boolean result = false;
        try {
            if (in != null) {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                int count;
                byte[] buffer = new byte[1024 * 8];
                while ((count = in.read(buffer, 0, buffer.length)) > 0) {
                    out.write(buffer, 0, count);
                }
                out.close();
                in.close();
                result = true;
            }
        } catch (IOException e) {
        } catch (Exception e) {
            LogWriter.e(e);
        }
        return result;
    }
}
