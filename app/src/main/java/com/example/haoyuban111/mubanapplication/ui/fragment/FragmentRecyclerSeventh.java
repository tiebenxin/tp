package com.example.haoyuban111.mubanapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterDrageItem;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostDoubleList;
import com.example.haoyuban111.mubanapplication.adapter.AdapterRecommendTravel;
import com.example.haoyuban111.mubanapplication.controller.ControllerDoubleImageList;
import com.example.haoyuban111.mubanapplication.controller.ControllerImageListNo;
import com.example.haoyuban111.mubanapplication.entity.Item;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;
import com.example.haoyuban111.mubanapplication.help_class.MyItemTouchCallback;
import com.example.haoyuban111.mubanapplication.help_class.OnRecyclerItemClickListener;
import com.example.haoyuban111.mubanapplication.ui.view.DividerGridItemDecoration;
import com.example.haoyuban111.mubanapplication.utils.ACache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class FragmentRecyclerSeventh extends FragmentBase {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_seventh, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View v = getView();
        ControllerImageListNo viewSingle = new ControllerImageListNo(v.findViewById(R.id.viewSingle));
        AdapterRecommendTravel mAdapterSingle = new AdapterRecommendTravel(ContextHelper.getApplicationContext());
        mAdapterSingle.setData(DataHelper.getSecondModels());
        viewSingle.setAdapter(mAdapterSingle);


        ControllerDoubleImageList viewDouble = new ControllerDoubleImageList(v.findViewById(R.id.viewDouble));
        AdapterHostDoubleList mAdapterDouble = new AdapterHostDoubleList(ContextHelper.getApplicationContext());
        mAdapterDouble.setData(DataHelper.getSecondModels());
        viewDouble.setAdapter(mAdapterDouble);
    }

}
