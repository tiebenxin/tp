package com.example.haoyuban111.mubanapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.controller.ControllerImage;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.IModelPrototype;

import java.util.List;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class AdapterImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> listItems;
    private final LayoutInflater inflater;


    public void setData(List<String> list) {
        listItems = list;
        notifyDataSetChanged();
    }

    public AdapterImage() {
        super();
        inflater = LayoutInflater.from(ContextHelper.getApplicationContext());

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_image, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder holderView = (RecyclerViewHolder) holder;
        if (listItems != null && listItems.size() > 0) {
            String url = listItems.get(position);
            Glide.with(ContextHelper.getApplicationContext())
                    .load(url).placeholder(R.drawable.default_male)
                    .centerCrop()
                    .error(R.drawable.default_male)
                    .into(holderView.iv);
//            holderView.iv.setImageResource(R.drawable.avatar_default_blue);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_img);
        }

        ImageView iv;
    }


}
