package com.example.haoyuban111.mubanapplication.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：liszt
 * 版    本：1.0
 * 创建日期：2016/5/21
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends EncryptCallback<T> {

    private Class<T> clazz;
    private Type type;
    private static Response _resp;

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public JsonCallback() {
        this.type = type;
    }

    //该方法是子线程处理，不能做ui相关的工作
    @Override
    public T convertSuccess(Response response) {
        try {
            _resp = response;
            String responseData = response.body().string();
            if (TextUtils.isEmpty(responseData)) return null;

            /**
             * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
             * 以下只是一个示例，具体业务具体实现
             */

            JSONObject jsonObject = new JSONObject(responseData);
            final String msg = jsonObject.optString("msg", "");
            final int code = jsonObject.optInt("code", 000000);
//            JSONObject data = jsonObject.optJSONObject("data");
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
                    if (clazz == String.class) return (T) data;
                    if (clazz != null) return new Gson().fromJson(data, clazz);
                    if (type != null) return new Gson().fromJson(data, type);
                    break;
                case 104:
                    //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等
                    break;
                case 105:
                    //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等
                    break;
                case 106:
                    //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等

                    break;
                case 300:
                    //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等
                    break;
                case 300001:
//                    IHPAccountEnvironment environment = HPAccountManager.getEnvironment();
//                    IntentActivitySplash intentLogOut = new IntentActivitySplash(ContextHelper.getApplicationContext());
//                    intentLogOut.setAuthorizationState(-1);
//                    intentLogOut.setTitle(ContextHelper.getString(R.string.connection_lost));
//                    intentLogOut.setMessage(ContextHelper.getString(R.string.account_click_time));
//                    HPAccountManager.logout(environment.getAccountID());
//                    Log.i("toke", "toke过期");
                    break;
                case 300002:
//                    IHPAccountEnvironment environment1 = HPAccountManager.getEnvironment();
//                    HPAccountManager.logout(environment1.getAccountID());
//                    Log.i("session", "session");
                    checkSession();
                    return null;
                case 900000:
                case 200001:

//                    System.out.print("详情的user_id为空");
                    return null;


            }
            //如果要更新UI，需要使用handler，可以如下方式实现，也可以自己写handler
            OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
                @Override
                public void run() {
//                      Log.e("OkHttpUtils", "Error code：" + code + "，Error message：" + msg);
//                    Toast.makeText(OkHttpUtils.getContext(), "Error message：" + code + "，Error message：" + msg, Toast.LENGTH_SHORT).show();
                }
            });
//            Log.e("OkHttpUtils", "Error code：" + code + "，Error message：" + msg);
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e("OkHttpUtils", "Net IO read error");
        } catch (JSONException e) {
            e.printStackTrace();
//            Log.e("OkHttpUtils", "JSON parsing exceptions");
        }
        return null;
    }


    public abstract void onResponse(boolean isFromCache, T t, Request request, Response response);


    @Override
    /** 对返回数据进行操作的回调， UI线程 */
    public void onSuccess(T t, Call call, Response response) {
        onResponse(false, t, call.request(), response);
    }

    @Override
    /** 缓存成功的回调,UI线程 */
    public void onCacheSuccess(T t, Call call) {
//        Log.d("JsonCallback CacheSucc", String.format("Call(%s)Error(%s)", call.request().url().toString(), call.request().method(), call.request().body().toString()));
        if (_resp != null)
            onResponse(true, null, call.request(), _resp);
        else
            Log.d("JsonCallback CacheSucc", "not returned to caller");
    }

    @Override
    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    public void onError(Call call, Response response, Exception e) {
        if (response == null) {
//            Log.d("JsonCallback666888","response==null");
            response = _resp;
        }
//        Log.d("JsonCallback Error", String.format("Call(%s)Error(%s)", call.request().url().toString(), call.request().method(), call.request().body().toString()));
//        Log.d("JsonCallback Error", Log.getStackTraceString(e));
        if (response != null)
            onResponse(false, null, call.request(), response);
        else
            Log.d("JsonCallback Error", "not returned to caller");
    }


    @Override
    /** 缓存失败的回调,UI线程 */
    public void onCacheError(Call call, Exception e) {
//        Log.d("JsonCallback CacheError", String.format("Call(%s)Error(%s)", call.request().url().toString(), call.request().method(), call.request().body().toString()));
//        Log.d("JsonCallback CacheError", Log.getStackTraceString(e));
        if (_resp != null)
            onResponse(true, null, call.request(), _resp);
        else
            Log.d("JsonCallback CacheError", "not returned to caller");

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
