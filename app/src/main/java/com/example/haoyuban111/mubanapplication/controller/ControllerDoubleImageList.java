package com.example.haoyuban111.mubanapplication.controller;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostDoubleList;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.ui.view.DividerGridItemDecoration;
import com.example.haoyuban111.mubanapplication.ui.view.HorizontalListView;
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;

/**
 * Created by haoyuban111 on 2017/4/6.
 */

public class ControllerDoubleImageList<T> implements View.OnClickListener {

    private TextView tv_title;
    private LinearLayout ll_title;
    private OnClickListener mListener;
    private ImageView iv_icon;
    private RecyclerView mRecyclerView;
    private AdapterHostDoubleList mAdapter;
    private final int MARGIN = 10;

    public ControllerDoubleImageList(View v) {
        init(v);
        setListener();
    }

    private void init(View v) {
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        iv_icon = (ImageView) v.findViewById(R.id.iv_icon);
        ll_title = (LinearLayout) v.findViewById(R.id.ll_title);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

    }

    public void setAdapter(AdapterHostDoubleList adapter) {
        if (adapter == null || adapter.getList().size() <= 0) {
            return;
        }

        mAdapter = adapter;

        GridLayoutManager linearLayoutManager = new GridLayoutManager(ContextHelper.getApplicationContext(), 2);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DividerGridItemDecoration decoration = new DividerGridItemDecoration(ContextHelper.getApplicationContext());
        decoration.setSize(DensityUtil.dip2px(ContextHelper.getApplicationContext(), MARGIN));
        decoration.setOrientation(DividerGridItemDecoration.HORIZONTAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(mAdapter);
        adapter.setItemClickListener(new AdapterHostDoubleList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mListener != null) {
                    mListener.clickItem(mAdapter.getList().get(position));
                }
            }
        });

    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setIcon(int drawableId) {
        iv_icon.setImageDrawable(ContextHelper.getDrawable(drawableId));
    }


    private void setListener() {
        ll_title.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == ll_title.getId()) {
            if (mListener != null) {
                mListener.clickNext();
            }
        }
    }

    public interface OnClickListener<T> {
        void clickNext();

        void clickItem(T t);
    }

}
