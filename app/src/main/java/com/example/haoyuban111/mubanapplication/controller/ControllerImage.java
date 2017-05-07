package com.example.haoyuban111.mubanapplication.controller;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.example.haoyuban111.mubanapplication.R;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class ControllerImage extends ControllerBase {
    private ImageView imageView;

    public ControllerImage(View view) {
        super(view);
        imageView = (ImageView) _view.findViewById(R.id.iv_img);
    }


    public ImageView getView() {
        return imageView;
    }

    public void setBitmap(BitmapDrawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public void setUrl(String url) {
        imageView.setImageDrawable(getDrawble(url));
    }

    private BitmapDrawable getDrawble(String url) {
        return null;
    }


}
