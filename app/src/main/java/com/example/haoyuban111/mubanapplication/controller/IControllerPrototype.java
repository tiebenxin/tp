package com.example.haoyuban111.mubanapplication.controller;

import android.view.View;

import com.example.haoyuban111.mubanapplication.model.IModelPrototype;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public interface IControllerPrototype {

    IControllerPrototype createInstance();

    View getView();

    void setModel(IModelPrototype model, int positionInList);
}
