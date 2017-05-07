package com.example.haoyuban111.mubanapplication.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Administrator on 2016/7/4.
 */

public class GlideConfigModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//和Picasso配置一样
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
