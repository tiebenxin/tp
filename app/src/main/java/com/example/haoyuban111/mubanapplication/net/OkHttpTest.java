package com.example.haoyuban111.mubanapplication.net;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by haoyuban111 on 2017/3/29.
 */

public class OkHttpTest {
//    public static void getUserDataTest(IHPAccountEnvironment environment, String userId) {
//        String action = "UserInfoProfileReviewList";
//        OkHttpUtils.postTest(GlobalConstants.getServerUrl(), action, environment)
//                .params("TravelHostUserId", userId)
//                .params("hostPageNum", String.valueOf(1))
//                .params("travelPageNum", String.valueOf(1))
//                .params("reviewPageNum", String.valueOf(1))
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        JSONObject object = TravelJsonHelper.changeUser(ResponseHelpter.getData(s));
//                        if (object != null) {
//                            UTravelUser user = UTravelUser.newInstance(object);
//                        }
//                    }
//                });
//
//
//    }

//    public static void getUserDataTest2(IHPAccountEnvironment environment, String userId) {
//        String action = "UserInfoProfileReviewList";
//        BasePostRequest request = new BasePostRequest(GlobalConstants.getServerUrl(), action, environment);
//        request.params("TravelHostUserId", userId)
//                .params("hostPageNum", String.valueOf(1))
//                .params("travelPageNum", String.valueOf(1))
//                .params("reviewPageNum", String.valueOf(1))
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new StringCallbackTest() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//
//                        JSONObject object = TravelJsonHelper.changeUser(ResponseHelpter.getData(s));
//                        if (object != null) {
//                            UTravelUser user = UTravelUser.newInstance(object);
//                        }
//                    }
//                });
//    }
}
