package com.example.haoyuban111.mubanapplication.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by haoyuban111 on 2017/4/26.
 */

public class ServiceUtils {

    //设置offer
    public static void setOfferedService(ViewGroup parent, String offer) {
        //添加服务标签  1: Bed，2: Drink，4: Guide，8: Consultant
        if (null == offer || "".equals(offer)) {
            parent.setVisibility(View.INVISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            ArrayList imgList = (ArrayList) getServices(offer);
            parent.removeAllViews();
            ImageView imageView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(ContextHelper.getApplicationContext(), 12), DensityUtil.dip2px(ContextHelper.getApplicationContext(), 12));
            for (int i = 0; i < imgList.size(); i++) {
                String str = (String) imgList.get(i);
                if (!"1".equals(str) && !"2".equals(str) && !"4".equals(str) && !"8".equals(str)) {
                    continue;
                }
                imageView = new ImageView(ContextHelper.getApplicationContext());
                if ("1".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_place_to_stay);
                } else if ("2".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_meal_together);
                } else if ("4".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_tavel_guide);
                } else if ("8".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_advice);
                }
                if (i > 0) {
                    params.setMargins(DensityUtil.dip2px(ContextHelper.getApplicationContext(), 4), 0, 0, 0);
                }
                imageView.setLayoutParams(params);
                parent.addView(imageView);
            }

        }
    }

    //    设置offer
    public static void setRequestService(ViewGroup parent, String request) {
        //添加服务标签  1: Bed，2: Drink，4: Guide，8: Consultant
        if (null == request || "".equals(request)) {
            parent.setVisibility(View.INVISIBLE);

        } else {
            parent.setVisibility(View.VISIBLE);
            ArrayList imgList = (ArrayList) getServices(request);
            parent.removeAllViews();
            ImageView imageView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(ContextHelper.getApplicationContext(), 12), DensityUtil.dip2px(ContextHelper.getApplicationContext(), 12));
            for (int i = 0; i < imgList.size(); i++) {
                String str = (String) imgList.get(i);
                if (!"1".equals(str) && !"2".equals(str) && !"4".equals(str) && !"8".equals(str)) {
                    continue;
                }
                imageView = new ImageView(ContextHelper.getApplicationContext());

                if ("1".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_place_to_stay_gray);
                } else if ("2".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_meal_together_gary);
                } else if ("4".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_tavel_guide_gray);
                } else if ("8".equals(str)) {
                    imageView.setImageResource(R.drawable.icon_advice_gary);
                }
                if (i > 0) {
                    params.setMargins(DensityUtil.dip2px(ContextHelper.getApplicationContext(), 4), 0, 0, 0);
                }
                imageView.setLayoutParams(params);
                parent.addView(imageView);
            }
        }
    }

    public static List getServices(String service) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> listTemp = new ArrayList<Integer>();
        if (null == service || "".equals(service)) {

        } else {
            String sr = service.trim();
            String[] s = sr.split(",");
            for (int i = 0; i < s.length; i++) {
                String str = s[i];
                if (str != null && !"".equals(str)&&!"null".equals(str)) {
                    listTemp.add(Integer.parseInt(str));
                }
            }
            Collections.sort(listTemp);
            for (int i = 0; i < listTemp.size(); i++) {
                list.add(listTemp.get(i) + "");
            }
        }
        return list;
    }
}
