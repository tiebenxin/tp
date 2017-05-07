/**
 * Copyright 2013 Mani Selvaraj
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

package com.vipbcw.netroid.toolbox;


import com.vipbcw.netroid.Listener;
import com.vipbcw.netroid.NetworkResponse;
import com.vipbcw.netroid.ParseError;
import com.vipbcw.netroid.Request;
import com.vipbcw.netroid.Response;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * MultipartRequest - To handle the large file uploads.
 * Extended from JSONRequest. You might want to change to StringRequest based on your response type.
 * @author Mani Selvaraj
 *
 */
public class MultiPartStringRequest extends Request<String> implements MultiPartRequest {

	/* To hold the parameter name and the File to upload */
	private Map<String,File> fileUploads = new HashMap<String,File>();

	/* To hold the parameter name and the string content to upload */
	private Map<String,String> stringUploads = new HashMap<String,String>();
	
    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     */
    public MultiPartStringRequest(int method, String url, Listener<String> listener) {
        super(method, url, listener);
    }

    public void addFileUpload(String param,File file) {
    	fileUploads.put(param,file);
    }
    
    public void addStringUpload(String param,String content) {
    	stringUploads.put(param,content);
    }
    
    /**
     * 要上传的文件
     */
    public Map<String,File> getFileUploads() {
    	return fileUploads;
    }
    
    /**
     * 要上传的参数
     */
    public Map<String,String> getStringUploads() {
    	return stringUploads;
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

	/**
	 * 空表示不上传
	 */
    public String getBodyContentType() {
        return null;
    }
}