package com.example.haoyuban111.mubanapplication.net;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by haoyuban111 on 2016/12/16.
 */

public class BasePostRequest extends BaseBodyRequest<BasePostRequest> {
    public BasePostRequest(String url, String action) {
        super(url);
//        headers("BundleID", My.Application.getPackageName());
//        headers("session", GlobalConstants.getSession(ContextHelper.getApplicationContext()));

//        params("signature", ServiceUtils.takeSignature(environment, action));
//        params("action", action);
//        params("AuthToken", ServiceUtils.takeAuthToken(environment));
//        params("UserId", ServiceUtils.takeUserId(environment));
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        try {
            headers.put(HttpHeaders.HEAD_KEY_CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            OkLogger.e(e);
        }
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.post(requestBody).url(url).tag(tag).build();    }
}
