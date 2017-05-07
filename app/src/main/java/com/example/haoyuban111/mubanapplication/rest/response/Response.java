package com.example.haoyuban111.mubanapplication.rest.response;

import com.example.haoyuban111.mubanapplication.rest.IMaintenanceRule;
import com.example.haoyuban111.mubanapplication.rest.MaintenanceRule;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

/**
 * Created by haoyuban111 on 2017/3/27.
 */

public class Response {
    private int _status;
    private byte[] _body;
    private final String _errorBody;

    public Response(int status, byte[] body) {
        _status = status;
        _body = body;
        _errorBody = null;
    }

    protected Response(int status, String errorBody) {
        _status = status;
        _errorBody = errorBody;
    }

    public int getStatus() {
        return _status;
    }

    public boolean isSuccessful() {
        return isSuccessful(_status);
    }

    public boolean isOK() {
        return _status == HttpURLConnection.HTTP_OK;
    }

    protected static boolean isSuccessful(int status) {
        return status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_CREATED || status == HttpURLConnection.HTTP_ACCEPTED;
    }

    public String bodyAsString() {
        try {
            return hasBody() ? bodyAsString(_body) : "";
        } catch (UnsupportedEncodingException e) {
//            LogWriter.e(e);
            return "";
        }
    }

    public byte[] getBody() {
        return _body;
    }

    public boolean hasBody() {
        return _body != null && _body.length > 0;
    }

    protected void clearBody() {
        _body = null;
    }

    public static String bodyAsString(byte[] body) throws UnsupportedEncodingException {
        return new String(body, "utf-8");
    }

    public IMaintenanceRule getMaintenance() {
        IMaintenanceRule result = null;
        if (withError() && getStatus() == HttpURLConnection.HTTP_UNAVAILABLE) {
            try {
                result = new MaintenanceRule(getErrorBody());
            } catch (Exception ex) {
                //nothing to do
            }
        }
        return result;
    }

    public boolean withError() {
        return _errorBody != null;
    }

    public String getErrorBody() {
        return _errorBody;
    }

    public static Response createWithError(int status, String errorBody) {
        return new Response(status, errorBody);
    }
}
