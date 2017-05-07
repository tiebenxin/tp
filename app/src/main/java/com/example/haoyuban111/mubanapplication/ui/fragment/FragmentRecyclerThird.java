package com.example.haoyuban111.mubanapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterPopularLocation;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;
import com.example.haoyuban111.mubanapplication.ui.view.DividerGridItemDecoration;
import com.example.haoyuban111.mubanapplication.ui.view.DividerItemDecoration;
import com.example.haoyuban111.mubanapplication.utils.ScreenUtils;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class FragmentRecyclerThird extends FragmentBase {

    private RecyclerView recyclerView;
    private final int MARGIN = 20;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        View v = getView();

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextHelper.getApplicationContext());
        DividerGridItemDecoration decoration = new DividerGridItemDecoration(ContextHelper.getApplicationContext(), R.drawable.divider, R.color.transparent);
        decoration.setSize(MARGIN);
        decoration.setType(DividerGridItemDecoration.BORDER);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        AdapterPopularLocation mAdapter = new AdapterPopularLocation(ContextHelper.getApplicationContext(), getSize());
        mAdapter.setData(DataHelper.getSecondModels());
        recyclerView.setAdapter(mAdapter);
    }

    private int getSize() {
        int screenWidth = ScreenUtils.getScreenWidth(ContextHelper.getApplicationContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        int marginWidth = params.leftMargin + params.rightMargin;
        int size = (screenWidth - marginWidth) / 2;
        return size;
    }

}
