package com.example.haoyuban111.mubanapplication.net;

/**
 * Created by haoyuban111 on 2016/12/16.
 */

public class OkHttpException extends Exception {

    public static OkHttpException INSTANCE(String msg) {
        return new OkHttpException(msg);
    }

    public OkHttpException() {
        super();
    }

    public OkHttpException(String detailMessage) {
        super(detailMessage);
    }

    public OkHttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public OkHttpException(Throwable throwable) {
        super(throwable);
    }
}
