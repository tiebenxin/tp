package com.example.haoyuban111.mubanapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostDoubleList;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostList;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostListSingle;
import com.example.haoyuban111.mubanapplication.adapter.AdapterRecommendHost;
import com.example.haoyuban111.mubanapplication.adapter.AdapterRecommendTravel;
import com.example.haoyuban111.mubanapplication.controller.ControllerBooks;
import com.example.haoyuban111.mubanapplication.controller.ControllerBottomTab;
import com.example.haoyuban111.mubanapplication.controller.ControllerDoubleImageList;
import com.example.haoyuban111.mubanapplication.controller.ControllerImageList;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;
import com.example.haoyuban111.mubanapplication.help_class.IEventListener;
import com.example.haoyuban111.mubanapplication.help_class.IFragmentBase;
import com.example.haoyuban111.mubanapplication.help_class.IFragmentBaseExtended;
import com.example.haoyuban111.mubanapplication.ui.view.HorizontalListView;
import com.example.haoyuban111.mubanapplication.ui.view.MyItemDecoration;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class FragmentRecyclerFirst extends FragmentBase implements IFragmentBaseExtended {

    private ControllerBooks viewBooks;
    private IEventListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        View v = getView();

        ControllerImageList viewSingle = new ControllerImageList(v.findViewById(R.id.viewSingle));
        AdapterRecommendHost mAdapter = new AdapterRecommendHost(ContextHelper.getApplicationContext());
        mAdapter.setData(DataHelper.getSecondModels());
        viewSingle.setAdapter(mAdapter);


        ControllerDoubleImageList viewDouble = new ControllerDoubleImageList(v.findViewById(R.id.viewDouble));
        AdapterHostDoubleList mAdapterDouble = new AdapterHostDoubleList(ContextHelper.getApplicationContext());
        mAdapterDouble.setData(DataHelper.getSecondModels());
        viewDouble.setAdapter(mAdapterDouble);

        viewBooks = new ControllerBooks(v.findViewById(R.id.viewBooks));
        viewBooks.setSelected(ControllerBooks.LEFT);
        viewBooks.setData(DataHelper.getSecondModels());
        viewBooks.getTxtHeader().setText("玄幻 | 奇幻 | 仙侠");
        viewBooks.setOnClickTabListener(new ControllerBooks.OnClickTabListener() {
            @Override
            public void clickLeft() {
                viewBooks.setData(DataHelper.getSecondModels());
                viewBooks.setSelected(ControllerBooks.LEFT);

            }

            @Override
            public void clickCenter() {
                viewBooks.setData(DataHelper.getSecondModels2());
                viewBooks.setSelected(ControllerBooks.CENTER);

            }

            @Override
            public void clickRight() {
                viewBooks.setData(DataHelper.getSecondModels());
                viewBooks.setSelected(ControllerBooks.RIGHT);

            }
        });

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
    public void setListener(IEventListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
