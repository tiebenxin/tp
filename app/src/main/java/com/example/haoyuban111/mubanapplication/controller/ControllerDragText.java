package com.example.haoyuban111.mubanapplication.controller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.GridViewSortAdapter;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.ui.view.DragSortGridView;

import java.util.List;

/**
 * Created by wl on 2017/4/26.
 */

public class ControllerDragText {

    private TextView tv_title;
    private DragSortGridView gridView;
    private OnClickListener mListener;
    private TextView tv_button;
    boolean isEdit = false;
    boolean isCompleted = false;
    private GridViewSortAdapter adapter;

    public ControllerDragText(View v, boolean flag) {
        isEdit = flag;
        init(v);

    }

    public void setType(boolean flag) {
        isEdit = flag;
        checkIsEdit();
    }

    private void init(View v) {
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        gridView = (DragSortGridView) v.findViewById(R.id.dragGridView);
        tv_button = (TextView) v.findViewById(R.id.tv_button);
        checkIsEdit();
    }

    private void checkIsEdit() {
        if (isEdit) {
            tv_button.setVisibility(View.VISIBLE);
            tv_button.setText("Edit");
        } else {
            tv_button.setVisibility(View.GONE);
        }
    }

    public void setTitle(String value) {
        tv_title.setText(value);
    }

    public void setData(List<String> list) {
        if (adapter == null) {
            adapter = new GridViewSortAdapter(gridView, ContextHelper.getApplicationContext(), mListener);
        }
        adapter.setData(list);
        gridView.setAdapter(adapter);
        getTotalHeightofListView(gridView);

        if (isEdit) {
            tv_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCompleted) {
                        adapter.setType(false);
                        tv_button.setText("Edit");
                        isCompleted = false;
                    } else {
                        adapter.setType(true);
                        tv_button.setText("Complete");
                        isCompleted = true;
                    }
                }
            });
        }
    }


    public static void getTotalHeightofListView(DragSortGridView gridView) {
        ListAdapter mAdapter = gridView.getAdapter();
        int count = mAdapter.getCount();
        int num = 4;
        if (mAdapter == null) {
            return;
        }

        int totalHeight = 0;
        View mView = mAdapter.getView(0, null, gridView);
        mView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mView.measure(0, 0);
        int n = count / num;
        int row = n;
        if (count > n * num) {
            row = n + 1;
        } else if (count < n * num) {
            row = n - 1;
        }

        totalHeight = row * mView.getMeasuredHeight();


        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }

    public interface OnClickListener {
        void clickDele(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

}
