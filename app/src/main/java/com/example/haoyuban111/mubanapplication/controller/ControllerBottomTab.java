package com.example.haoyuban111.mubanapplication.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.ui.activity.ActivityFifth;
import com.example.haoyuban111.mubanapplication.ui.activity.ActivityFirst;
import com.example.haoyuban111.mubanapplication.ui.activity.ActivityForth;
import com.example.haoyuban111.mubanapplication.ui.activity.ActivitySecond;
import com.example.haoyuban111.mubanapplication.ui.activity.ActivityThird;

/**
 * Created by haoyuban111 on 2017/3/14.
 */

public class ControllerBottomTab implements View.OnClickListener {
    EBottomTabs mCurrentTabs = EBottomTabs.FIRST;
    Activity mActivity;
    private LinearLayout bar_first;
    private LinearLayout bar_second;
    private LinearLayout bar_third;
    private LinearLayout bar_forth;
    private LinearLayout bar_fifth;
    View rootView;

    public ControllerBottomTab(Activity activity, View view, EBottomTabs tabs) {
        mCurrentTabs = tabs;
        mActivity = activity;
        rootView = view;
        if (mCurrentTabs != null && mActivity != null) {
            initView(view);
        }


    }

    private void initView(View view) {
        bar_first = (LinearLayout) view.findViewById(R.id.bar_first);
        bar_second = (LinearLayout) view.findViewById(R.id.bar_second);
        bar_third = (LinearLayout) view.findViewById(R.id.bar_third);
        bar_forth = (LinearLayout) view.findViewById(R.id.bar_forth);
        bar_fifth = (LinearLayout) view.findViewById(R.id.bar_fifth);
        ImageView iv_first = (ImageView) view.findViewById(R.id.iv_first);
        ImageView iv_second = (ImageView) view.findViewById(R.id.iv_second);
        ImageView iv_third = (ImageView) view.findViewById(R.id.iv_third);
        ImageView iv_forth = (ImageView) view.findViewById(R.id.iv_forth);
        ImageView iv_fifth = (ImageView) view.findViewById(R.id.iv_fifth);
        TextView tv_first = (TextView) view.findViewById(R.id.tv_first);
        TextView tv_second = (TextView) view.findViewById(R.id.tv_second);
        TextView tv_third = (TextView) view.findViewById(R.id.tv_third);
        TextView tv_forth = (TextView) view.findViewById(R.id.tv_forth);
        TextView tv_fifth = (TextView) view.findViewById(R.id.tv_fifth);
        bar_first.setOnClickListener(this);
        bar_second.setOnClickListener(this);
        bar_third.setOnClickListener(this);
        bar_forth.setOnClickListener(this);
        bar_fifth.setOnClickListener(this);

        iv_first.setSelected(mCurrentTabs == EBottomTabs.FIRST ? true : false);
        iv_second.setSelected(mCurrentTabs == EBottomTabs.SECOND ? true : false);
        iv_third.setSelected(mCurrentTabs == EBottomTabs.THIRD ? true : false);
        iv_forth.setSelected(mCurrentTabs == EBottomTabs.FORTH ? true : false);
        iv_fifth.setSelected(mCurrentTabs == EBottomTabs.FIFITH ? true : false);

        tv_first.setTextColor(mCurrentTabs == EBottomTabs.FIRST ? mActivity.getResources().getColor(R.color.colorPrimary) : mActivity.getResources().getColor(R.color.txt_gray));
        tv_second.setTextColor(mCurrentTabs == EBottomTabs.SECOND ? mActivity.getResources().getColor(R.color.colorPrimary) : mActivity.getResources().getColor(R.color.txt_gray));
        tv_third.setTextColor(mCurrentTabs == EBottomTabs.THIRD ? mActivity.getResources().getColor(R.color.colorPrimary) : mActivity.getResources().getColor(R.color.txt_gray));
        tv_forth.setTextColor(mCurrentTabs == EBottomTabs.FORTH ? mActivity.getResources().getColor(R.color.colorPrimary) : mActivity.getResources().getColor(R.color.txt_gray));
        tv_fifth.setTextColor(mCurrentTabs == EBottomTabs.FIFITH ? mActivity.getResources().getColor(R.color.colorPrimary) : mActivity.getResources().getColor(R.color.txt_gray));
    }

    private void setVisible(boolean flag) {
        rootView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (view.getId() == bar_first.getId()) {
            if (mCurrentTabs == EBottomTabs.FIRST) {
                return;
            }
            intent.setClass(mActivity, ActivityFirst.class);
            mActivity.startActivity(intent);
        } else if (view.getId() == bar_second.getId()) {
            if (mCurrentTabs == EBottomTabs.SECOND) {
                return;
            }
            intent.setClass(mActivity, ActivitySecond.class);
            mActivity.startActivity(intent);
        } else if (view.getId() == bar_third.getId()) {
            if (mCurrentTabs == EBottomTabs.THIRD) {
                return;
            }
            intent.setClass(mActivity, ActivityThird.class);
            mActivity.startActivity(intent);
        } else if (view.getId() == bar_forth.getId()) {
            if (mCurrentTabs == EBottomTabs.FORTH) {
                return;
            }
            intent.setClass(mActivity, ActivityForth.class);
            mActivity.startActivity(intent);
        } else if (view.getId() == bar_fifth.getId()) {
            if (mCurrentTabs == EBottomTabs.FIFITH) {
                return;
            }
            intent.setClass(mActivity, ActivityFifth.class);
            mActivity.startActivity(intent);
        }
    }

    public enum EBottomTabs {
        FIRST,
        SECOND,
        THIRD,
        FORTH,
        FIFITH
    }


}
