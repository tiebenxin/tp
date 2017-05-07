package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2017/4/10.
 */

public abstract class AbstractRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mBeanList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public AbstractRecyclerAdapter(Context ctx) {
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
        mBeanList = new ArrayList<T>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setData(List<T> l) {
        mBeanList = l;
    }

    public List<T> getList() {
        return mBeanList;
    }

}