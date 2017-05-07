package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class AdapterHostListSingle<T> extends BaseAdapter {
    private OnItemClickListener mItemClickListener;
    private final LayoutInflater inflater;
    private final Context mContext;
    private List<T> datas;

    public AdapterHostListSingle(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        mContext = context;

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
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (datas != null && datas.size() > 0) {
            T model = datas.get(position);
            final ControllerListSingle controller;
            if (convertView == null) {
                controller = new ControllerListSingle();
                convertView = controller.getView();
                convertView.setTag(controller);
            } else {
                controller = (ControllerListSingle) convertView.getTag();
            }
            controller.setModel(model);

            if (position < getCount() - 1) {
                controller.setMargin();
            }
            return convertView;
        }


        return null;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ControllerListSingle {

        RoundImageView iv_image;
        ImageView iv_trust;
        TextView tv_area, tv_name;
        LinearLayout ll_parent, ll_root;
        private View _view;

        public View getView() {
            if (_view == null) {
                _view = inflater.inflate(R.layout.item_manage_list, null);
                loadControls(_view);
            }
            return _view;
        }

        private void loadControls(View v) {
            iv_image = (RoundImageView) v.findViewById(R.id.iv_image);
            iv_trust = (ImageView) v.findViewById(R.id.iv_trust);
            tv_area = (TextView) v.findViewById(R.id.tv_area);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            ll_parent = (LinearLayout) v.findViewById(R.id.ll_parent);
            ll_root = (LinearLayout) v.findViewById(R.id.ll_root);
        }

        private void setModel(T t) {
            if (t instanceof String) {
                String name = (String) t;
                tv_name.setText(name);
                tv_area.setText(name);
            } else if (t instanceof SecondModel) {
                SecondModel model = (SecondModel) t;
                Glide.with(ContextHelper.getApplicationContext())
                        .load(model.getAvatarUrl())
                        .centerCrop()
                        .error(R.drawable.avatar_default_blue)
                        .into(iv_image);
                tv_name.setText(model.getName());
                tv_area.setText(model.getText());
            }


        }


        private void setMargin() {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, DensityUtil.dip2px(ContextHelper.getApplicationContext(), 10), 0);
            ll_root.setLayoutParams(params);
        }


    }
}