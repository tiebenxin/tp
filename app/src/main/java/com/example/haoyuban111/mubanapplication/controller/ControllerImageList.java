package com.example.haoyuban111.mubanapplication.controller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.ui.view.HorizontalListView;


/**
 * Created by haoyuban111 on 2017/4/6.
 */

public class ControllerImageList<T> implements View.OnClickListener {

    private TextView tv_title;
    private LinearLayout ll_title;
    private BaseAdapter _adapter;
    private HorizontalListView listView;
    private OnClickListener mListener;
    private ImageView iv_icon;
    private View view;

    public ControllerImageList(View v) {
        init(v);
        setListener();
        view = v;
    }

    private void init(View v) {
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        iv_icon = (ImageView) v.findViewById(R.id.iv_icon);
        ll_title = (LinearLayout) v.findViewById(R.id.ll_title);
        listView = (HorizontalListView) v.findViewById(R.id.hlistview);
        listView.setFocusable(false);
    }

    public void setAdapter(BaseAdapter adapter) {
        _adapter = adapter;
        listView.setAdapter(_adapter);
        _adapter.notifyDataSetChanged();
        if (_adapter.getCount() > 0) {
            getTotalHeightofListView(listView);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mListener != null) {
                    mListener.clickItem(_adapter.getItem(position));
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

    public void setVisible(boolean flag) {
        view.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
}
