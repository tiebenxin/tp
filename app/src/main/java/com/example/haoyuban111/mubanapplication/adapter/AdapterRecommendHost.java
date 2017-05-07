package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebIconDatabase;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.SecondModel;
import com.example.haoyuban111.mubanapplication.ui.view.RoundImageView;
import com.example.haoyuban111.mubanapplication.utils.ServiceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/7.
 */

public class AdapterRecommendHost<T> extends BaseAdapter {
    List<T> mList;
    private final Context mContext;
    LayoutInflater _inflater;

    public AdapterRecommendHost(Context context) {
        mContext = context;
        _inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<T> list) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.clear();
        mList.addAll(list);

    }

    public List<T> getList() {
        return mList;
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
        if (mList != null && mList.size() > 0) {
            T model = mList.get(position);
            final ControllerRecommend controller;
            if (convertView == null) {
                controller = new ControllerRecommend();
                convertView = controller.getView();
                convertView.setTag(controller);
            } else {
                controller = (ControllerRecommend) convertView.getTag();
            }
            controller.setModel(model);
            return convertView;
        }
        return null;
    }


    public class ControllerRecommend {
        RoundImageView iv_image;
        ImageView iv_trust;
        TextView tv_area, tv_name;
        LinearLayout ll_parent;
        View _view;

        public View getView() {
            if (_view == null) {
                _view = _inflater.inflate(R.layout.item_manage_list, null);
                loadControls();
            }
            return _view;
        }

        private void loadControls() {
            iv_image = (RoundImageView) _view.findViewById(R.id.iv_image);
            iv_trust = (ImageView) _view.findViewById(R.id.iv_trust);
            tv_area = (TextView) _view.findViewById(R.id.tv_area);
            tv_name = (TextView) _view.findViewById(R.id.tv_name);
            ll_parent = (LinearLayout) _view.findViewById(R.id.ll_parent);
        }

        private void setModel(T t) {
            initView(t);
        }

        private void initView(T t) {
            SecondModel model = (SecondModel) t;
            ServiceUtils.setOfferedService(ll_parent, "1,2,4,8");
            Glide.with(ContextHelper.getApplicationContext())
                    .load(model.getAvatarUrl())
                    .centerCrop()
                    .error(R.drawable.avatar_default_red)
                    .into(iv_image);
//            iv_image.setImageDrawable(ContextHelper.getDrawable(R.drawable.avatar_default_gray));
            tv_name.setText(model.getName());
            tv_area.setText(model.getText());

        }

    }
}
