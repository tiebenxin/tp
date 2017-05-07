package com.example.haoyuban111.mubanapplication.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class ControllerHeader implements View.OnClickListener {

    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_title;
    private OnClickListener mListener;

    public ControllerHeader(View v) {
        init(v);
        setListener();
    }

    private void init(View v) {
        iv_left = (ImageView) v.findViewById(R.id.iv_left);
        iv_right = (ImageView) v.findViewById(R.id.iv_right);
        tv_title = (TextView) v.findViewById(R.id.tv_title);
    }

    private void setListener() {
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
    }

    public void setTitle(String value) {
        tv_title.setText(value);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_left.getId()) {
            if (mListener != null) {
                mListener.clickLeft();
            }
        } else if (v.getId() == iv_right.getId()) {
            if (mListener != null) {
                mListener.clickRight();
            }
        }

    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void clickLeft();

        void clickRight();
    }


}
