package com.example.haoyuban111.mubanapplication.rest;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class BasicNameValuePair extends NameValuePair {
    private final String _name;
    private final String _value;

    public BasicNameValuePair(String name, String value) {
        this._name = name;
        this._value = value;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getValue() {
        return _value;
    }
}
