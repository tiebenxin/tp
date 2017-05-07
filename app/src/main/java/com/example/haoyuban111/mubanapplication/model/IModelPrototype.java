package com.example.haoyuban111.mubanapplication.model;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public interface IModelPrototype {
    int getType();

    IModelPrototype createInstance();
}
