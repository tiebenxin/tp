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
import com.example.haoyuban111.mubanapplication.adapter.AbstractRecyclerAdapter;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostDoubleList;
import com.example.haoyuban111.mubanapplication.adapter.AdapterRecommendTravel;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.ui.view.DividerGridItemDecoration;
import com.example.haoyuban111.mubanapplication.ui.view.HorizontalListView;
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;

/**
 * Created by haoyuban111 on 2017/4/26.
 */

public class ControllerImageListNo implements View.OnClickListener {
    private TextView tv_title;
    private LinearLayout ll_title;
    private ControllerDoubleImageList.OnClickListener mListener;
    private ImageView iv_icon;
    private RecyclerView mRecyclerView;
    private AdapterRecommendTravel mAdapter;
    private final int MARGIN = 10;

    public ControllerImageListNo(View v) {
        init(v);
        setListener();
    }

    private void init(View v) {
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        iv_icon = (ImageView) v.findViewById(R.id.iv_icon);
        ll_title = (LinearLayout) v.findViewById(R.id.ll_title);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

    }

    public void setAdapter(AdapterRecommendTravel adapter) {
        if (adapter == null || adapter.getList().size() <= 0) {
            return;
        }

        mAdapter = adapter;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContextHelper.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerGridItemDecoration decoration = new DividerGridItemDecoration(ContextHelper.getApplicationContext());
        decoration.setSize(DensityUtil.dip2px(ContextHelper.getApplicationContext(), MARGIN));
        decoration.setOrientation(DividerGridItemDecoration.HORIZONTAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(mAdapter);
        adapter.setItemClickListener(new AdapterRecommendTravel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object bean) {
                if (mListener != null) {
                    mListener.clickItem(bean);
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

    public void setOnClickListener(ControllerDoubleImageList.OnClickListener listener) {
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

    public static void getTotalHeightofListView(HorizontalListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return;
        }

        int totalHeight = 0;
        View mView = mAdapter.getView(0, null, listView);
        mView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mView.measure(0, 0);
        totalHeight = mView.getMeasuredHeight();


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
