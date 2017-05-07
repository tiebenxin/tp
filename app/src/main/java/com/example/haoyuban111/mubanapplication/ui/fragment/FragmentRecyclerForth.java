package com.example.haoyuban111.mubanapplication.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostListSingle;
import com.example.haoyuban111.mubanapplication.adapter.AdapterPopularLocation;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.DataHelper;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.net.BitmapDialogCallback;
import com.example.haoyuban111.mubanapplication.net.OkHttpUtils;
import com.example.haoyuban111.mubanapplication.ui.view.DividerItemDecoration;
import com.example.haoyuban111.mubanapplication.ui.view.DragGridView;
import com.example.haoyuban111.mubanapplication.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class FragmentRecyclerForth extends FragmentBase {

    private final int MARGIN = 20;
    private AdapterHostListSingle mAdapter;
    private List<SecondModel> mList;
    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_forth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        View v = getView();

        DragGridView gridView = (DragGridView) v.findViewById(R.id.gridView);
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
            itemHashMap.put("item_image", R.drawable.default_male);
            itemHashMap.put("item_text", "拖拽 " + Integer.toString(i));
            dataSourceList.add(itemHashMap);
        }


        final SimpleAdapter mSimpleAdapter = new SimpleAdapter(getActivity(), dataSourceList,
                R.layout.item_drag_image, new String[]{"item_image", "item_text"},
                new int[]{R.id.item_image, R.id.item_text});

        gridView.setAdapter(mSimpleAdapter);

        gridView.setOnChangeListener(new DragGridView.OnChanageListener() {

            @Override
            public void onChange(int from, int to) {
                HashMap<String, Object> temp = dataSourceList.get(from);
                //这里的处理需要注意下
                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(dataSourceList, i, i + 1);
                    }
                } else if (from > to) {
                    for (int i = from; i > to; i--) {
                        Collections.swap(dataSourceList, i, i - 1);
                    }
                }

                dataSourceList.set(to, temp);

                mSimpleAdapter.notifyDataSetChanged();
            }
        });

    }


    private void getImage(final String url) {
        OkHttpUtils.get(url)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new BitmapDialogCallback(getActivity()) {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
//                        handleResponse(bitmap, call, response);
//                        iv_avatar.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        handleError(call, response);
                    }
                });
    }


}
