package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.ui.view.RoundImageView;
import com.example.haoyuban111.mubanapplication.utils.ServiceUtils;

import java.util.List;

/**
 * Created by Mark on 2017/4/25.
 */

public class AdapterRecommendTravel extends AbstractRecyclerAdapter {

    private OnItemClickListener mItemClickListener;

    public AdapterRecommendTravel(Context ctx) {
        super(ctx);
    }

    public void setData(List list) {
        mBeanList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_manage_list, parent, false);
        return new RecommendTravelViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((AbstractViewHolder) holder).bindHolder(mBeanList.get(position));
    }

    class RecommendTravelViewHolder extends AbstractViewHolder {

        RoundImageView iv_image;
        ImageView iv_trust;
        TextView tv_area, tv_name;
        LinearLayout ll_parent;
        Handler mHandler = new Handler();

        public RecommendTravelViewHolder(View itemView) {
            super(itemView);
            iv_image = (RoundImageView) itemView.findViewById(R.id.iv_image);
            iv_trust = (ImageView) itemView.findViewById(R.id.iv_trust);
            tv_area = (TextView) itemView.findViewById(R.id.tv_area);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_parent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
        }

        @Override
        public void bindHolder(final Object bean) {
            SecondModel model = (SecondModel) bean;
            ServiceUtils.setOfferedService(ll_parent, "1,2,4,8");
//            iv_image.setImageDrawable(ContextHelper.getDrawable(R.drawable.avatar_default_gray));
            Glide.with(ContextHelper.getApplicationContext())
                    .load(model.getAvatarUrl())
                    .centerCrop()
                    .error(R.drawable.avatar_default_gray)
                    .into(iv_image);
            tv_name.setText(model.getName());
            tv_area.setText(model.getText());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object bean);
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
