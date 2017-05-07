package com.example.haoyuban111.mubanapplication.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.controller.ControllerDragText;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class FragmentRecyclerEighth extends FragmentBase {

    private List<String> list1;
    private List<String> list2;
    private ControllerDragText viewFirst;
    private ControllerDragText viewSecond;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_eighth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
                dismiss();
            }
        }, 2000);
    }

    private void initView() {
        View v = getView();
        List<String> list = DataHelper.getStringList();
        int size = list.size();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list1.addAll(list.subList(0, size / 2));
        list2.addAll(list.subList(size / 2, size));

        viewFirst = new ControllerDragText(v.findViewById(R.id.viewSingle), true);
        viewFirst.setTitle("喜欢");
        viewFirst.setOnClickListener(new ControllerDragText.OnClickListener() {
            @Override
            public void clickDele(int position) {
                String s = list1.get(position);
                list1.remove(position);
                list2.add(s);
                viewFirst.setData(list1);
                viewSecond.setData(list2);

            }
        });
        viewFirst.setData(list1);


        viewSecond = new ControllerDragText(v.findViewById(R.id.viewDouble), false);
        viewSecond.setTitle("推荐");
        viewSecond.setData(list2);
    }


}
