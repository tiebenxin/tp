package com.example.haoyuban111.mubanapplication.rest.request;

import com.example.haoyuban111.mubanapplication.rest.BasicNameValuePair;
import com.example.haoyuban111.mubanapplication.rest.EHttpMethod;
import com.example.haoyuban111.mubanapplication.rest.IStreamReader;
import com.example.haoyuban111.mubanapplication.rest.NameValuePair;
import com.example.haoyuban111.mubanapplication.rest.response.Response;


import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestDownloadFile extends AbstractRequest<Response> {

    private final IStreamReader _streamReader;
    private final String _url;
    private final int _maxAge;
    private List<NameValuePair> _headers;

    public RequestDownloadFile(String url, IStreamReader streamReader, int maxAge) {
        _streamReader = streamReader;
        _url = url;
        _maxAge = maxAge;
    }

    @Override
    public EHttpMethod getMethod() {
        return EHttpMethod.GET;
    }

    @Override
    public String getUrl() {
        return _url;
    }

    @Override
    protected List<NameValuePair> getParameters() {
        return null;
    }

    @Override
    public List<NameValuePair> getHeaders() {
        final List<NameValuePair> headers = super.getHeaders();
        headers.add(new BasicNameValuePair("Connection", "close"));
        if(_headers != null){
            headers.addAll(_headers);
        }
        return headers;
    }

    @Override
    protected Response createResponse(int responseCode, Map<String, List<String>> headerFields, InputStream inStream) throws IOException, JSONException {
        _streamReader.read(inStream, getContentLength(headerFields));
        return new Response(responseCode, null);
    }

    private static int getContentLength(Map<String, List<String>> headers){
        final List<String> items = headers.get("Content-Length");
        int result = -1;
        if(items != null && items.size() > 0){
            try{
                result = Integer.parseInt(items.get(0));
            } catch (Exception ex){
//                LogWriter.e(ex);
            }
        }
        return result;
    }

    @Override
    protected String getContentType() {
        return "";
    }

    @Override
    protected void writeBody(HttpURLConnection connection) throws IOException {

    }

    @Override
    protected boolean withCache() {
        return _maxAge > 0;
    }

    @Override
    protected int getCacheMaxAge() {
        return _maxAge;
    }

    @Override
    protected boolean isAllowRedirect() {
        return true;
    }

    public void addHeader(String key, String value){
        if(_headers == null){
            _headers = new ArrayList<NameValuePair>();
        }
        _headers.add(new BasicNameValuePair(key, value));
    }
}
