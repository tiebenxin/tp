package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.ui.view.RoundImageView;
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by haoyuban111 on 2016/11/5.
 */
public class AdapterPopularLocation<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener mItemClickListener;
    private final LayoutInflater inflater;
    private final Context mContext;
    private List<T> datas;
    private int mSize;

    public AdapterPopularLocation(Context context, int size) {
        super();
        inflater = LayoutInflater.from(context);
        mContext = context;
        mSize = size;

    }

    public void setData(List<T> list) {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final ChildViewHolder holder = (ChildViewHolder) viewHolder;
        final T model = datas.get(position);
        holder.setModel(model);

        if (mItemClickListener != null && model != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(model);
                }
            });
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int viewType) {
        View view = inflater.inflate(R.layout.item_popular_country, null);
        ChildViewHolder holder = new ChildViewHolder(view);
        return holder;
    }


    public interface OnItemClickListener<T> {
        void onItemClick(T t);
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public ChildViewHolder(View v) {
            super(v);
            iv_image = (RoundImageView) v.findViewById(R.id.iv_image);
            tv_area = (TextView) v.findViewById(R.id.tv_area);
            rl_parent = (RelativeLayout) v.findViewById(R.id.rl_parent);
        }

        RoundImageView iv_image;
        TextView tv_area;
        SecondModel model;
        RelativeLayout rl_parent;

        private void setModel(T t) {
            if (t instanceof SecondModel) {
                model = (SecondModel) t;
                Glide.with(ContextHelper.getApplicationContext())
                        .load(model.getAvatarUrl())
                        .centerCrop()
                        .error(R.drawable.avatar_default_black)
                        .into(iv_image);
                tv_area.setText(model.getName());
            }
        }

        private void updateWidth() {
            iv_image.setMaxWidth(DensityUtil.dip2px(ContextHelper.getApplicationContext(), mSize));
            iv_image.setMaxHeight((180 * DensityUtil.dip2px(ContextHelper.getApplicationContext(), mSize)) / 168);
            notifyDataSetChanged();
        }

        private View createView(ViewGroup parent) {
            if (iv_image == null) {
                iv_image = new RoundImageView(ContextHelper.getApplicationContext());
                iv_image.setMaxWidth(DensityUtil.dip2px(ContextHelper.getApplicationContext(), mSize));
                iv_image.setMaxHeight((180 * DensityUtil.dip2px(ContextHelper.getApplicationContext(), mSize)) / 168);
                iv_image.setBackground(ContextHelper.getDrawable(R.drawable.avatar_default_yellow));
                iv_image.setType(RoundImageView.TYPE_ROUND);
                iv_image.setBorderRadius(5);
            }

            if (tv_area == null) {
                tv_area = new TextView(ContextHelper.getApplicationContext());
                tv_area.setTextColor(ContextHelper.getColor(R.color.white));
                tv_area.setTextSize(DensityUtil.sp2px(ContextHelper.getApplicationContext(), 18));
                tv_area.setGravity(Gravity.CENTER);
            }
            parent.removeAllViews();
            parent.addView(iv_image);
            parent.addView(tv_area);

            parent.setMinimumWidth(DensityUtil.dip2px(ContextHelper.getApplicationContext(), mSize));
            parent.setMinimumHeight((180 * DensityUtil.dip2px(ContextHelper.getApplicationContext(), mSize)) / 168);

            return parent;


        }

    }
}