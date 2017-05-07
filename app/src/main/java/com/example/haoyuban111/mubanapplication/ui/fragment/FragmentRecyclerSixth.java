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
import com.example.haoyuban111.mubanapplication.entity.Item;
import com.example.haoyuban111.mubanapplication.help_class.MyItemTouchCallback;
import com.example.haoyuban111.mubanapplication.help_class.OnRecyclerItemClickListener;
import com.example.haoyuban111.mubanapplication.ui.view.DividerGridItemDecoration;
import com.example.haoyuban111.mubanapplication.utils.ACache;
import com.example.haoyuban111.mubanapplication.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class FragmentRecyclerSixth extends FragmentBase implements MyItemTouchCallback.OnDragListener {

    private List<Item> results = new ArrayList<Item>();
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(getActivity()).getAsObject("items");
        if (items != null)
            results.addAll(items);
        else {
            for (int i = 0; i < 3; i++) {
                results.add(new Item(i * 8 + 0, "收款", R.drawable.takeout_ic_category_brand));
                results.add(new Item(i * 8 + 1, "转账", R.drawable.takeout_ic_category_flower));
                results.add(new Item(i * 8 + 2, "余额宝", R.drawable.takeout_ic_category_fruit));
                results.add(new Item(i * 8 + 3, "手机充值", R.drawable.takeout_ic_category_medicine));
                results.add(new Item(i * 8 + 4, "医疗", R.drawable.takeout_ic_category_motorcycle));
                results.add(new Item(i * 8 + 5, "彩票", R.drawable.takeout_ic_category_public));
                results.add(new Item(i * 8 + 6, "电影", R.drawable.takeout_ic_category_store));
                results.add(new Item(i * 8 + 7, "游戏", R.drawable.takeout_ic_category_sweet));
            }
        }
        results.remove(results.size() - 1);
        results.add(new Item(results.size(), "更多", R.drawable.takeout_ic_more));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterDrageItem adapter = new AdapterDrageItem(R.layout.item_grid, results);
        recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != results.size() - 1) {
                    itemTouchHelper.startDrag(vh);
//                    VibratorUtil.Vibrate(getActivity(), 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Item item = results.get(vh.getLayoutPosition());
                Toast.makeText(getActivity(), item.getId() + " " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFinishDrag() {
        //存入缓存
        ACache.get(getActivity()).put("items", (ArrayList<Item>) results);
    }
}
