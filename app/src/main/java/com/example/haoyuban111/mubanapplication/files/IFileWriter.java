package com.example.haoyuban111.mubanapplication.files;


import java.io.File;
import java.io.InputStream;

public interface IFileWriter {
    boolean  write(InputStream stream, File file);
}
