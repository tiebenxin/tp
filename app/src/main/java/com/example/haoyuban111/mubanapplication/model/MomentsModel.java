package com.example.haoyuban111.mubanapplication.model;

import com.example.haoyuban111.mubanapplication.glide.CustomImageSizeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class MomentsModel implements IModelPrototype {
    private String name = "";
    private CustomImageSizeModel sizeModel;
    private String text = "";
    private List<String> imgList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return sizeModel.getBaseUrl();
    }

    public void setSizeModel(CustomImageSizeModel sizeModel) {
        this.sizeModel = sizeModel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
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
