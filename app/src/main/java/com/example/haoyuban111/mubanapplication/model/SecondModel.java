package com.example.haoyuban111.mubanapplication.model;

import com.example.haoyuban111.mubanapplication.glide.CustomImageSizeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class SecondModel implements IModelPrototype {
    private String name = "";
    private String image = "";
    private String text = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return image;
    }

    public void setAvatarUrl(String url) {
        this.image = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public IModelPrototype createInstance() {
        return null;
    }
}
