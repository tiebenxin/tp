package com.example.haoyuban111.mubanapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.controller.ControllerBottomTab;
import com.example.haoyuban111.mubanapplication.controller.ControllerDateSelector;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.ui.view.CustomCalendarCardDialog;

import java.util.ArrayList;
import java.util.List;

public class ActivityFifth extends BaseActivity {

    private List<String> listDate;
    private ControllerDateSelector viewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        initView();

    }

    @Override
    protected void initView() {
        super.initView();
        ControllerBottomTab mBottomTabs = new ControllerBottomTab(this, findViewById(R.id.viewBottom), ControllerBottomTab.EBottomTabs.FIFITH);

        listDate = new ArrayList<>();
        viewDate = new ControllerDateSelector(findViewById(R.id.viewDate));
        viewDate.initDate("2017-04-23 00:00:00", "2017-04-26 00:00:00");
        viewDate.setOnClickListener(new ControllerDateSelector.OnSelectClickListener() {
            @Override
            public void click() {
                CustomCalendarCardDialog dialog = new CustomCalendarCardDialog(ActivityFifth.this, R.style.CustomCalendarDialog, listDate);
                dialog.show();
                dialog.setOnClickSureListenter(new CustomCalendarCardDialog.OnClickSureListenter() {
                    @Override
                    public void getSelectDate(ArrayList<String> list) {
                        if (null != listDate && listDate.size() > 0) {
                            listDate.clear();
                        }
                        listDate.addAll(list);
                        viewDate.setDataToControl(list);
                    }
                });
            }
        });

    }
}
