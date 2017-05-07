package com.example.haoyuban111.mubanapplication.controller;

import android.view.View;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class ControllerBase {
    protected View _view;
    public ControllerBase(View view) {
        _view = view;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        _view.setOnClickListener(listener);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        _view.setOnLongClickListener(listener);
    }

    public int getViewId() {
        return _view.getId();
    }

    public void setVisibility(boolean isVisible) {
        _view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setEnable(boolean isEnable) {
        _view.setEnabled(isEnable);
    }

    public View getView() {
        return _view;
    }

    public void setBackColor(int color) {
        getView().setBackgroundColor(color);
    }

    public void setPaddingLeft(int left) {
        getView().setPadding(left, getView().getPaddingTop(), getView().getPaddingRight(), getView().getPaddingBottom());
    }

    public void setPaddingTop(int top) {
        getView().setPadding(getView().getPaddingLeft(), top, getView().getPaddingRight(), getView().getPaddingBottom());
    }

    public void setPaddingRight(int right) {
        getView().setPadding(getView().getPaddingLeft(), getView().getPaddingTop(), right, getView().getPaddingBottom());
    }

    public void setPaddingBottom(int bottom) {
        getView().setPadding(getView().getPaddingLeft(), getView().getPaddingTop(), getView().getPaddingRight(), bottom);
    }

}
