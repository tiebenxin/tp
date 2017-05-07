package com.example.haoyuban111.mubanapplication.log;

public class LogWriter implements ILogWriter {

    private static final LogWriter INSTANCE;

    static {
        INSTANCE = new LogWriter();
    }

    private ILogWriter _externalLogWriter;

    private void setExternalLogWriter(ILogWriter externalLogWriter) {
        _externalLogWriter = externalLogWriter;
    }

    @Override
    public void eW(Throwable ex) {
        eW("", ex);
    }

    @Override
    public void eW(String message, Throwable ex) {
        if (_externalLogWriter != null) {
            _externalLogWriter.eW(message, ex);
        }
    }

    @Override
    public void dW(String message) {
        if (_externalLogWriter != null) {
            _externalLogWriter.dW(message);
        }
    }

    @Override
    public void debugW(String message) {
        if(_externalLogWriter != null){
            _externalLogWriter.debugW(message);
        }
    }

    public static void dHttp(boolean logging, String message){
        if (logging) INSTANCE.dW(message);
    }

    public static void configure(ILogWriter externalLogWriter) {
        INSTANCE.setExternalLogWriter(externalLogWriter);
    }

    public static void e(Throwable ex) {
        INSTANCE.eW(ex);
    }

    public static void e(String message, Throwable ex) {
        INSTANCE.eW(message, ex);
    }

    public static void d(String message) {
        INSTANCE.dW(message);
    }

    public static void debug(String message) {
        INSTANCE.debugW(message);
    }
}
