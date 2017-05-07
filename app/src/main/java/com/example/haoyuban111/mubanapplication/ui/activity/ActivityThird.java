package com.example.haoyuban111.mubanapplication.ui.activity;

import android.os.Bundle;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.controller.ControllerBottomTab;
import com.example.haoyuban111.mubanapplication.controller.ControllerHeader;

public class ActivityThird extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initView();

    }

    @Override
    protected void initView() {
        super.initView();
        ControllerBottomTab mBottomTabs = new ControllerBottomTab(this, findViewById(R.id.viewBottom), ControllerBottomTab.EBottomTabs.THIRD);

        ControllerHeader viewHeader = new ControllerHeader(findViewById(R.id.viewHeader));
        viewHeader.setTitle("Third");
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
