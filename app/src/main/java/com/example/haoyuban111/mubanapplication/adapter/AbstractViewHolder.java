package com.example.haoyuban111.mubanapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mark on 2017/4/10.
 */

public abstract class AbstractViewHolder<T> extends RecyclerView.ViewHolder {


    public AbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(T bean);

}