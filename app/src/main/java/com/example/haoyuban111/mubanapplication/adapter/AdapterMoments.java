package com.example.haoyuban111.mubanapplication.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.controller.ControllerImage;
import com.example.haoyuban111.mubanapplication.controller.ControllerMoments;
import com.example.haoyuban111.mubanapplication.controller.IControllerPrototype;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.IModelPrototype;
import com.example.haoyuban111.mubanapplication.model.MomentsModel;

import java.util.List;
import java.util.Map;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class AdapterMoments extends BaseAdapter {
    private Activity mActivity;

    public AdapterMoments(Activity activity){
        mActivity = activity;
    };

    private List<MomentsModel> listItems;

    private Map<Integer, IControllerPrototype> _prototypes;

    public void setData(List<MomentsModel> list) {
        listItems = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public MomentsModel getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MomentsModel model = getItem(position);
        ControllerMoments controller = null;
        if (convertView == null) {
            controller = new ControllerMoments(View.inflate(ContextHelper.getApplicationContext(), R.layout.item_moments, null),mActivity);
            convertView = controller.getView();
            convertView.setTag(controller);
        } else {
            controller = (ControllerMoments) convertView.getTag();
        }
        controller.setData(model);
        return convertView;
    }
}
