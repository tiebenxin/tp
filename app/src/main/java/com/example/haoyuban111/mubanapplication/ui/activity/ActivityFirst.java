package com.example.haoyuban111.mubanapplication.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterMoments;
import com.example.haoyuban111.mubanapplication.controller.ControllerBottomTab;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;

import java.util.List;

public class ActivityFirst extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ControllerBottomTab mBottomTabs = new ControllerBottomTab(this, findViewById(R.id.viewBottom), ControllerBottomTab.EBottomTabs.FIRST);
        ListView listView = (ListView) findViewById(R.id.lv);
        AdapterMoments adapter = new AdapterMoments(this);
        adapter.setData(DataHelper.getMoments());
        listView.setAdapter(adapter);

    }




    @Override
    public void onClick(View view) {


    }
}
