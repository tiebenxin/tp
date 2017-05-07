package com.example.haoyuban111.mubanapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyuban111 on 2016/11/5.
 */
public class AdapterHostList<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener mItemClickListener;
    private final int TYPE_NORMAL = 0;
    private final int TYPE_LONG = 1;
    private final LayoutInflater inflater;
    private final Context mContext;
    private List<T> datas;

    public AdapterHostList(Context context) {
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
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final ChildViewHolder holder = (ChildViewHolder) viewHolder;
        T model = datas.get(position);
        holder.setModel(model);

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int viewType) {
        View view = inflater.inflate(R.layout.item_manage_list, null);
        ChildViewHolder holder = new ChildViewHolder(view);
        return holder;


    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public ChildViewHolder(View v) {
            super(v);
            iv_image = (RoundImageView) v.findViewById(R.id.iv_image);
            iv_trust = (ImageView) v.findViewById(R.id.iv_trust);
            tv_area = (TextView) v.findViewById(R.id.tv_area);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            ll_parent = (LinearLayout) v.findViewById(R.id.ll_parent);
            ll_root = (LinearLayout) v.findViewById(R.id.ll_root);
        }

        RoundImageView iv_image;
        ImageView iv_trust;
        TextView tv_area, tv_name;
        LinearLayout ll_parent, ll_root;

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
                        .error(R.drawable.default_male)
                        .into(iv_image);
                tv_name.setText(model.getName());
                tv_area.setText(model.getText());
            }

        }


    }
}