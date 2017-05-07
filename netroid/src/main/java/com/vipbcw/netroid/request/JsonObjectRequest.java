/*
 * Copyright (C) 2015 Vince Styling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vipbcw.netroid.request;

import com.vipbcw.netroid.AuthFailureError;
import com.vipbcw.netroid.IListener;
import com.vipbcw.netroid.Listener;
import com.vipbcw.netroid.NetworkResponse;
import com.vipbcw.netroid.ParseError;
import com.vipbcw.netroid.Response;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A request for retrieving a {@link JSONObject} response body at a given URL, allowing for an
 * optional {@link JSONObject} to be passed in as part of the request body.
 */
public class JsonObjectRequest extends JsonRequest<String> {

    /**
     * Creates a new request.
     *
     * @param method      the HTTP method to use
     * @param url         URL to fetch the JSON from
     * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and
     *                    indicates no parameters will be posted along with request.
     * @param listener    Listener to receive the JSON response or error message
     */
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, IListener<String> listener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener);
    }

    public JsonObjectRequest(String url, String jsonRequest, IListener<String> listener) {
        super(Method.POST, url, jsonRequest, listener);
    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is
     * <code>null</code>, <code>POST</code> otherwise.
     *
     * @see #JsonObjectRequest(int, String, JSONObject, IListener)
     */
    public JsonObjectRequest(String url, JSONObject jsonRequest, IListener<String> listener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest, listener);
    }


    public JsonObjectRequest(String url, HttpEntity entity, Listener<String> listener) {
        super(Method.POST, url, null, listener);
        postEntity = entity;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, response.charset);
            return Response.success(jsonString, response);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }
}
