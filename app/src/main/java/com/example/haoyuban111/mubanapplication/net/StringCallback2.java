package com.example.haoyuban111.mubanapplication.net;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by haoyuban111 on 2016/12/16.
 */

public abstract class StringCallback2 extends AbsCallback<String> {

    private int code;

    @Override
    public String convertSuccess(Response response) throws Exception {
        String responseData = getResponseBody(response);

        if (TextUtils.isEmpty(responseData)) return null;

        /**
         * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
         * 以下只是一个示例，具体业务具体实现
         */

        JSONObject jsonObject = new JSONObject(responseData);
        code = jsonObject.optInt("code", 000000);
        String data = jsonObject.optString("data", "");
        switch (code) {
            case 000000:
                /**
                 * code = 0 代表成功，默认实现了Gson解析成相应的实体Bean返回，可以自己替换成fastjson等
                 * 对于返回参数，先支持 String，然后优先支持class类型的字节码，最后支持type类型的参数
                 */
                if (data == null || "".equals(data) || "[]".equals(data)) {
                    return null;
                }
                return data;
            case 300001:
//                IHPAccountEnvironment environment = HPAccountManager.getEnvironment();
//                IntentActivitySplash intentLogOut = new IntentActivitySplash(ContextHelper.getApplicationContext());
//                intentLogOut.setAuthorizationState(-1);
//                intentLogOut.setTitle(ContextHelper.getString(R.string.connection_lost));
//                intentLogOut.setMessage(ContextHelper.getString(R.string.account_click_time));
//                HPAccountManager.logout(environment.getAccountID());
                break;
            case 300002:
                checkSession();
                return null;
            case 900000:
            case 200001:

                return null;
        }
        response.close();
        return null;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        onResponse(false, s, call.request(), response, code);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
    }

    public abstract void onResponse(boolean isFromCache, String s, Request request, Response response, int code);

    private String getResponseBody(Response response) throws Exception {
        return StringConvert.create().convertSuccess(response);
    }


    private void checkSession() {
//        if (GlobalConstants.getSession(ContextHelper.getApplicationContext()) == null || "".equals(GlobalConstants.getSession(ContextHelper.getApplicationContext()))) {
//            if (ServiceUtils.isUseridExit()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            RequestTravelSession request = new RequestTravelSession(HPAccountManager.getEnvironment().getRequestContext());
//                            ResponseTravelSession response = request.execute();
//                            if (null != response) {
//                                String sesion = response.getSession();
//                                if (null != sesion && !"".equals(sesion)) {
//                                    TextViewUtils.saveSharedPreferencesNew(ContextHelper.getApplicationContext(), "MyCurrentSession", sesion, "session");
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
//            }
//
//        }
    }
}
