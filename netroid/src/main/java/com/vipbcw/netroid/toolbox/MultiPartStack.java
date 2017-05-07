package com.vipbcw.netroid.toolbox;



import com.vipbcw.netroid.AuthFailureError;
import com.vipbcw.netroid.Request;
import com.vipbcw.netroid.stack.HurlStack;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhaotengfei
 * @version 1.0.0
 * @created 2016-4-20
 */
public class MultiPartStack extends HurlStack {
	@SuppressWarnings("unused")
	private static final String TAG = MultiPartStack.class.getSimpleName();
    private final static String HEADER_CONTENT_TYPE = "Content-Type";

	public MultiPartStack(String userAgent) {
		super(userAgent);
	}

	@Override
	public HttpResponse performRequest(Request<?> request) throws IOException, AuthFailureError {
		
		if(!(request instanceof MultiPartRequest)) {
			return super.performRequest(request);
		}
		else {
			return performMultiPartRequest(request);
		}
	}
	
    private static void addHeaders(HttpUriRequest httpRequest, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }
    }
	
	public HttpResponse performMultiPartRequest(Request<?> request)  throws IOException, AuthFailureError {
        HttpUriRequest httpRequest = createMultiPartRequest(request);
        addHeaders(httpRequest, request.getHeaders());
        HttpParams httpParams = httpRequest.getParams();
        int timeoutMs = request.getTimeoutMs();

        if(timeoutMs != -1) {
        	HttpConnectionParams.setSoTimeout(httpParams, timeoutMs);
        }
        
        /* Make a thread safe connection manager for the client */
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        return httpClient.execute(httpRequest);
	}
	
	

    static HttpUriRequest createMultiPartRequest(Request<?> request) throws AuthFailureError {
        switch (request.getMethod()) {
            case Request.Method.GET:
                return new HttpGet(request.getUrl());
            case Request.Method.DELETE:
                return new HttpDelete(request.getUrl());
            case Request.Method.POST: {
                HttpPost postRequest = new HttpPost(request.getUrl());
                if(request.getBodyContentType() != null) {
                	postRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                }
                postRequest.addHeader("enctype", "multipart/form-data");
                setMultiPartBody(postRequest,request);
                return postRequest;
            }
            case Request.Method.PUT: {
                HttpPut putRequest = new HttpPut(request.getUrl());
                if(request.getBodyContentType() != null)
                	putRequest.addHeader(HEADER_CONTENT_TYPE, request.getBodyContentType());
                setMultiPartBody(putRequest,request);
                return putRequest;
            }
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }
	
	/**
	 * If Request is MultiPartRequest type, then set MultipartEntity in the
	 * httpRequest object.
	 * 
	 * @param httpRequest
	 * @param request
	 * @throws AuthFailureError
	 */
	private static void setMultiPartBody(HttpEntityEnclosingRequestBase httpRequest,
			Request<?> request) throws AuthFailureError {

		// Return if Request is not MultiPartRequest
		if (!(request instanceof MultiPartRequest)) {
			return;
		}

		// MultipartEntity multipartEntity = new
		// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		/* example for setting a HttpMultipartMode */
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		// Iterate the fileUploads
		Map<String, File> fileUpload = ((MultiPartRequest) request).getFileUploads();
		for (Map.Entry<String, File> entry : fileUpload.entrySet()) {

			builder.addPart(((String) entry.getKey()), new FileBody((File) entry.getValue()));
		}
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
		// Iterate the stringUploads
		Map<String, String> stringUpload = ((MultiPartRequest) request).getStringUploads();
		for (Map.Entry<String, String> entry : stringUpload.entrySet()) {
			try {
				builder.addPart(((String) entry.getKey()),
						new StringBody((String) entry.getValue(), contentType));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		httpRequest.setEntity(builder.build());
	}

}
