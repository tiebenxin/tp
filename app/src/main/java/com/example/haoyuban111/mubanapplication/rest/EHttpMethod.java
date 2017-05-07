package com.example.haoyuban111.mubanapplication.rest;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public enum EHttpMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");

    private final String _value;

    EHttpMethod(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }
}
