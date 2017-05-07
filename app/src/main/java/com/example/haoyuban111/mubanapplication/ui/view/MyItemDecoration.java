package com.example.haoyuban111.mubanapplication.ui.view;

import android.graphics.Rect;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;


public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int margin;
    private RecyclerView.LayoutManager layoutManager;


    public MyItemDecoration(int margin, RecyclerView.LayoutManager manager) {
        this.margin = margin;
        layoutManager = manager;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (layoutManager instanceof GridLayoutManager) {
            outRect.left = 10;
            if (pos % 2 == 0) {  //下面一行
                outRect.set(margin, 0, 0, margin);
            } else { //上面一行
                outRect.set(margin, 0, margin, margin);
            }
        } else if (layoutManager instanceof LinearLayoutManager) {

        }

    }


}