package com.example.haoyuban111.mubanapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterPopularLocation;
import com.example.haoyuban111.mubanapplication.controller.ControllerBottomTab;
import com.example.haoyuban111.mubanapplication.controller.ControllerHeader;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;
import com.example.haoyuban111.mubanapplication.ui.view.DividerItemDecoration;
import com.example.haoyuban111.mubanapplication.utils.ScreenUtils;

public class ActivityForth extends BaseActivity {
    private final int MARGIN = 20;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);
        initView();

    }

    @Override
    protected void initView() {
        super.initView();
        ControllerBottomTab mBottomTabs = new ControllerBottomTab(this, findViewById(R.id.viewBottom), ControllerBottomTab.EBottomTabs.FORTH);

        ControllerHeader viewHeader = new ControllerHeader(findViewById(R.id.viewHeader));
        viewHeader.setTitle("Forth");
        viewHeader.setOnClickListener(new ControllerHeader.OnClickListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {

            }
        });

    }
}
