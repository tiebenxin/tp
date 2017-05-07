package com.example.haoyuban111.mubanapplication.rest;


import java.io.IOException;
import java.io.InputStream;

public interface IStreamReader {
    public void read(InputStream stream, int contentLength) throws IOException;
}
