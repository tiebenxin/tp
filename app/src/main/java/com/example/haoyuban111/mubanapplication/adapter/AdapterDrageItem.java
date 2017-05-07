package com.example.haoyuban111.mubanapplication.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.entity.Item;
import com.example.haoyuban111.mubanapplication.help_class.MyItemTouchCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/25.
 */

public class AdapterDrageItem extends RecyclerView.Adapter<AdapterDrageItem.MyViewHolder> implements MyItemTouchCallback.ItemTouchAdapter {
    private Context context;
    private int src;
    private List<Item> results;

    public AdapterDrageItem(int src, List<Item> results) {
        this.results = results;
        this.src = src;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(src, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setImageResource(results.get(position).getImg());
        holder.textView.setText(results.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition == results.size() - 1 || toPosition == results.size() - 1) {
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = width / 4;
            itemView.setLayoutParams(layoutParams);
            textView = (TextView) itemView.findViewById(R.id.item_text);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
