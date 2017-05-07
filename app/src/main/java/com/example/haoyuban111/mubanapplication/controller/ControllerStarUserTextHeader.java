package com.example.haoyuban111.mubanapplication.controller;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;


public class ControllerStarUserTextHeader extends ControllerBase {

    private View mView;
    private TextView _txtHeader;

    public ControllerStarUserTextHeader(View view) {
        super(view);

        mView = _view.findViewById(R.id.inHeader);
        _txtHeader = (TextView) mView.findViewById(R.id.txtHeader);
    }

    public ControllerStarUserTextHeader(View view, int id) {
        super(view);

        _txtHeader = (TextView) mView.findViewById(id);
    }

    public TextView getTextView() {
        return _txtHeader;
    }

    public void setText(CharSequence text) {
        _txtHeader.setText(text);
    }

    public void setTypeFace(Typeface typeFace) {
        _txtHeader.setTypeface(typeFace);
    }

    public void setBackColor(int color) {
        _txtHeader.setBackgroundColor(color);
    }

    public void setTextColor(int color) {
        _txtHeader.setTextColor(color);
    }

    public void setTxtAlpha(float alpha) {
        _txtHeader.setAlpha(alpha);
    }

    public void setTxtSize(float size) {
        _txtHeader.setTextSize(size);
    }
}
