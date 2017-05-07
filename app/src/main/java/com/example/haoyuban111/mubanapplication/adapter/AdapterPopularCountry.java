package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * Created by haoyuban111 on 2017/4/7.
 */

public class AdapterPopularCountry<T> extends BaseAdapter {
    List<T> mList;
    private final Context mContext;

    public AdapterPopularCountry(Context context) {
        mContext = context;
    }

    public void setData(List<T> list) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        final ChildViewHolder holder;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_popular_country, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);

        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }
        SecondModel model = (SecondModel) mList.get(position);
        if (model != null) {
            Glide.with(ContextHelper.getApplicationContext())
                    .load(model.getAvatarUrl())
                    .centerCrop()
                    .error(R.drawable.default_male)
                    .into(holder.iv_image);
            holder.tv_area.setText(model.getName());
        }


        return view;
    }


    public class ChildViewHolder {
        public ChildViewHolder(View v) {
            iv_image = (RoundImageView) v.findViewById(R.id.iv_image);
            tv_area = (TextView) v.findViewById(R.id.tv_area);
            rl_parent = (RelativeLayout) v.findViewById(R.id.rl_parent);
            updateWidth();
        }

        RoundImageView iv_image;
        TextView tv_area;
        RelativeLayout rl_parent;

        private void updateWidth() {
            iv_image.setMaxWidth(DensityUtil.dip2px(ContextHelper.getApplicationContext(), 360));
            iv_image.setMaxHeight((180 * DensityUtil.dip2px(ContextHelper.getApplicationContext(), 360)) / 168);
            iv_image.invalidate();
        }
    }


}
