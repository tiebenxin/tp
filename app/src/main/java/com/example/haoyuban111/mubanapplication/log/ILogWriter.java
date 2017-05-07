package com.example.haoyuban111.mubanapplication.log;

public interface ILogWriter {
    void eW(Throwable ex);

    void eW(String message, Throwable ex);

    void dW(String message);

    void debugW(String message);
}
