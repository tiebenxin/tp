package com.example.haoyuban111.mubanapplication.controller;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostListSingle;
import com.example.haoyuban111.mubanapplication.adapter.AdapterImage;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;
import com.example.haoyuban111.mubanapplication.model.IModelPrototype;
import com.example.haoyuban111.mubanapplication.model.MomentsModel;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.ui.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class ControllerBooks<T> implements View.OnClickListener {

    public final static int LEFT = 0;
    public final static int CENTER = 1;
    public final static int RIGHT = 2;
    private TextView tv_left, tv_center, tv_right;
    protected View _view;
    private LinearLayout ll_left;
    private LinearLayout ll_center;
    private LinearLayout ll_right;
    private HorizontalListView lv_list;
    private OnClickTabListener mListener;
    private List<T> datas;
    private AdapterHostListSingle mAdapterSingle;
    private TextView _txtHeader;


    public ControllerBooks(View view) {
        init(view);
        _view = view;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        setListener();
    }

    public ControllerBooks(int layId) {
        View view = View.inflate(ContextHelper.getApplicationContext(), layId, null);
        init(view);
        _view = view;
        setListener();
    }

    public TextView getTxtHeader() {
        if (_txtHeader == null) {
            ControllerStarUserTextHeader controller = new ControllerStarUserTextHeader(_view);
            _txtHeader = controller.getTextView();
        }
        return _txtHeader;
    }


    public void setData(List<T> list) {
        if (datas != null) {
            datas.clear();
            datas.addAll(list);
        }
        mAdapterSingle.setData(datas);
        lv_list.setAdapter(mAdapterSingle);
        mAdapterSingle.notifyDataSetChanged();
        getTotalHeightofListView(lv_list);
    }

    private void init(View view) {
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_center = (TextView) view.findViewById(R.id.tv_center);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        ll_left = (LinearLayout) view.findViewById(R.id.ll_left);
        ll_center = (LinearLayout) view.findViewById(R.id.ll_center);
        ll_right = (LinearLayout) view.findViewById(R.id.ll_right);

        lv_list = (HorizontalListView) view.findViewById(R.id.lv_list);
        mAdapterSingle = new AdapterHostListSingle(ContextHelper.getApplicationContext());

    }

    public void setSelected(int index) {
        if (index == RIGHT) {
            ll_left.setSelected(false);
            ll_center.setSelected(false);
            ll_right.setSelected(true);
        } else if (index == CENTER) {
            ll_left.setSelected(false);
            ll_center.setSelected(true);
            ll_right.setSelected(false);
        } else {
            ll_left.setSelected(true);
            ll_center.setSelected(false);
            ll_right.setSelected(false);
        }
    }


    private void setListener() {
        ll_center.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_right.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
        if (v.getId() == ll_left.getId()) {
            if (mListener != null) {
                mListener.clickLeft();
            }
        } else if (v.getId() == ll_center.getId()) {
            if (mListener != null) {
                mListener.clickCenter();
            }
        } else if (v.getId() == ll_right.getId()) {
            if (mListener != null) {
                mListener.clickRight();
            }
        }
    }

    public void setOnClickTabListener(OnClickTabListener listener) {
        mListener = listener;
    }

    public interface OnClickTabListener {
        void clickLeft();

        void clickCenter();

        void clickRight();
    }
}
