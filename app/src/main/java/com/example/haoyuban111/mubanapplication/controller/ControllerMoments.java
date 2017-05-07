package com.example.haoyuban111.mubanapplication.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterImage;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.model.IModelPrototype;
import com.example.haoyuban111.mubanapplication.model.MomentsModel;
import com.example.haoyuban111.mubanapplication.net.BaseRequest;
import com.example.haoyuban111.mubanapplication.net.BitmapDialogCallback;
import com.example.haoyuban111.mubanapplication.net.FileCallback;
import com.example.haoyuban111.mubanapplication.net.OkHttpUtils;
import com.example.haoyuban111.mubanapplication.ui.view.DividerGridItemDecoration;
import com.example.haoyuban111.mubanapplication.utils.DensityUtil;
import com.example.haoyuban111.mubanapplication.utils.Urls;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class ControllerMoments implements IControllerPrototype {

    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_content;
    protected View _view;
    private RecyclerView recyclerView;
    private AdapterImage adapterImage;
    private LinearLayout ll_left;
    private LinearLayout ll_right;
    public final static String TAG = "GlideHolder";
    private String url = "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg";
    private String AvaUrl = "http://img2.vipbcw.com/2017/03/8d36e32f59b4dcf733d92dae8146bc37";
    protected TextView responseData;
    private Activity mActivity;


    public ControllerMoments(View view, Activity activity) {
        init(view);
        _view = view;
        mActivity = activity;
    }

    public ControllerMoments(int layId) {
        View view = View.inflate(ContextHelper.getApplicationContext(), layId, null);
        init(view);
        _view = view;
    }

    private void init(View view) {
        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        ll_left = (LinearLayout) view.findViewById(R.id.ll_left);
        ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapterImage = new AdapterImage();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ContextHelper.getApplicationContext(), 3);
        DividerGridItemDecoration decoration = new DividerGridItemDecoration(ContextHelper.getApplicationContext());
        decoration.setSize(DensityUtil.dip2px(ContextHelper.getApplicationContext(), 10));
        decoration.setType(DividerGridItemDecoration.BORDER);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterImage);

    }

    public void setData(MomentsModel model) {
        if (model == null) {
            return;
        }
        Glide.with(ContextHelper.getApplicationContext())
                .load(model.getAvatarUrl())
                .error(R.drawable.avatar_default_yellow)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv_avatar);
//        getImage(model.getAvatarUrl());

        tv_name.setText(model.getName());
        tv_content.setText(model.getText());
        adapterImage.setData(model.getImgList());
        adapterImage.notifyDataSetChanged();
        setBottomHeight(ll_left, ll_right);

    }

    private void setBottomHeight(View left, View right) {
        right.setMinimumHeight(0);

        right.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measureHeight = right.getMeasuredHeight();

        int minHeight = 0;
        if (measureHeight > minHeight) {
            minHeight = measureHeight;

        }
        left.setMinimumHeight(minHeight);
        right.setMinimumHeight(minHeight);
    }

    @Override
    public IControllerPrototype createInstance() {
        return null;
    }

    @Override
    public View getView() {
        return _view;
    }

    @Override
    public void setModel(IModelPrototype model, int positionInList) {

    }


    private void getImage(final String url) {
        OkHttpUtils.get(url)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new BitmapDialogCallback(mActivity) {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
//                        handleResponse(bitmap, call, response);
                        iv_avatar.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        handleError(call, response);
                    }
                });
    }


}
