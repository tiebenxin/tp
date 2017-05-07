package com.example.haoyuban111.mubanapplication.controller;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.ui.view.CustomCalendarCardDialog;
import com.example.haoyuban111.mubanapplication.utils.DateDFUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/21.
 */

public class ControllerDateSelector implements View.OnClickListener {

    private LinearLayout ll_start;
    private LinearLayout ll_end;
    private TextView tv_end;
    private TextView tv_start;
    List<String> listDate;
    private OnSelectClickListener mListener;

    public ControllerDateSelector(View v) {
        init(v);
        setListener();
        if (listDate == null) {
            listDate = new ArrayList<>();
        }
    }

    public void initDate(String start, String end) {
        if (listDate == null) {
            listDate = new ArrayList<>();
        }

        if (start == null) {
            start = "";
        }

        if (end == null) {
            end = "";
        }
        listDate.clear();
        listDate.add(start);
        listDate.add(end);
    }

    private void init(View v) {
        ll_start = (LinearLayout) v.findViewById(R.id.ll_start);
        ll_end = (LinearLayout) v.findViewById(R.id.ll_end);
        tv_end = (TextView) v.findViewById(R.id.tv_end);
        tv_start = (TextView) v.findViewById(R.id.tv_start);
    }

    private void setListener() {
        ll_start.setOnClickListener(this);
        ll_end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ll_end.getId()) {
            if (mListener != null) {
                mListener.click();
            }
        } else if (v.getId() == ll_start.getId()) {
            if (mListener != null) {
                mListener.click();
            }
        }
    }

    public void setDataToControl(List<String> list) {
        if (null != list && list.size() > 0) {
            if (list.size() == 1) {//不会出现这种情况
                if (null != list.get(0) && !"".equals(list.get(0))) {

                    tv_start.setText(DateDFUtils.getLongDate(list.get(0)));
                } else {
                    tv_start.setText(ContextHelper.getString(R.string.no_sure));
                }
                tv_end.setText(ContextHelper.getString(R.string.no_sure));

            } else if (list.size() == 2) {
                if (null != list.get(0) && !"".equals(list.get(0))) {
                    tv_start.setText(DateDFUtils.getLongDate(list.get(0)));
                } else {
                    tv_start.setText(ContextHelper.getString(R.string.no_sure));
                }
                if (null != list.get(1) && !"".equals(list.get(1))) {
                    tv_end.setText(DateDFUtils.getLongDate(list.get(1)));
                } else {
                    tv_end.setText(ContextHelper.getString(R.string.no_sure));
                }

            }
        } else {
            tv_start.setText(ContextHelper.getString(R.string.no_sure));
            tv_end.setText(ContextHelper.getString(R.string.no_sure));
        }
    }

    public void setOnClickListener(OnSelectClickListener listener) {
        mListener = listener;
    }

    public interface OnSelectClickListener {
        void click();
    }
}
