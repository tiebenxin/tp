package com.example.haoyuban111.mubanapplication.rest.request;

import android.text.TextUtils;

import com.example.haoyuban111.mubanapplication.rest.BasicNameValuePair;
import com.example.haoyuban111.mubanapplication.rest.EHttpMethod;
import com.example.haoyuban111.mubanapplication.rest.IHPRequestContext;
import com.example.haoyuban111.mubanapplication.rest.NameValuePair;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public abstract class HPAbstractRequest<T, V extends IHPRequestContext> {

    private static final int CONNECTION_TIMEOUT = 120000;
    private static final int READ_TIMEOUT = 120000;

    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    private final V _requestContext;

    protected HPAbstractRequest(V requestContext) {
        _requestContext = requestContext;
    }


    public abstract EHttpMethod getMethod();

    public abstract String getUrl();

    protected abstract List<NameValuePair> getParameters();

    protected abstract T createResponse(int responseCode, Map<String, List<String>> headerFields, InputStream inStream) throws IOException, JSONException;

    protected T createWithError(int responseCode, String errorBody) {
        return null;
    }

    protected int getConnectTimeout() {
        return CONNECTION_TIMEOUT;
    }

    protected int getReadTimeout() {
        return READ_TIMEOUT;
    }

    protected abstract String getContentType();

    protected String getContentEncoding() {
        return null;
    }

    protected Integer getAdminChannel() {
        return null;
    }

    public List<NameValuePair> getHeaders() {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();
        addParameter(headers, "Content-Type", getContentType());
        addParameter(headers, "Content-Encoding", getContentEncoding());
        addParameter(headers, "AdminChannel", getAdminChannel());
        if (withCache()) {
            addParameter(headers, "Cache-Control", String.format(Locale.US, "max-age=%d", getCacheMaxAge()));
        } else {
            addParameter(headers, "Cache-Control", "no-cache");
        }
//        addBundleID(headers);
        return headers;
    }

    protected void writeBody(HttpURLConnection connection) throws IOException {

    }

    public T execute() throws IOException {
        String url = createURL();
        return execute(url);
    }

    private T execute(String address) throws IOException {
//        LogWriter.dHttp(AppConfig.INSTANCE.getSettingsDeveloper().isLogHTTP(), address);
        HttpURLConnection httpConnection = null;
        T response = null;
        int status = -1;
        try {
            final URL url = new URI(address).toURL();
            final URLConnection urlConnection = url.openConnection();
            httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setInstanceFollowRedirects(true);
            urlConnection.setConnectTimeout(getConnectTimeout());
            urlConnection.setReadTimeout(getReadTimeout());
            urlConnection.setUseCaches(withCache());

            final SSLContext sslContext = getSSLContext();
            if (sslContext != null) {
                ((HttpsURLConnection) urlConnection).setSSLSocketFactory(sslContext.getSocketFactory());
            }

            final List<NameValuePair> headers = getHeaders();
            if (headers != null) {
                for (NameValuePair header : headers) {
                    urlConnection.addRequestProperty(header.getName(), header.getValue());
                }
            }
            final EHttpMethod method = getMethod();
            httpConnection.setRequestMethod(method.getValue());
            if (method == EHttpMethod.POST) {
                httpConnection.setDoOutput(true);
                writeBody(httpConnection);
            }
            status = httpConnection.getResponseCode();

            if (isSuccessful(status)) {
                if (isAllowRedirect() && isRedirect(status) && method == EHttpMethod.GET) {
                    String newUrl = httpConnection.getHeaderField("Location");
                    if (!TextUtils.isEmpty(newUrl)) {
                        if (httpConnection.getContentLength() > 0) {
                            readToArray(new BufferedInputStream(httpConnection.getInputStream()));
                        }
                        return execute(newUrl);
                    }
                }

                BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
                try {
                    response = createResponse(status, httpConnection.getHeaderFields(), in);
                } finally {
                    try {
                        in.close();
                    } catch (Exception ex) {
                        //nothing to do
                    }
                }
            } else {

                final String error = readErrorStream(httpConnection);
                //try to create default response
                response = createWithError(status, error);
                if (response == null) {
                    writeLog(status, address, error);
                }
            }
        } catch (Exception ex) {
            writeLog(status, address, readErrorStream(httpConnection));
            caughtException(ex);
        } finally {
            if (httpConnection != null)
                httpConnection.disconnect();
        }

        return response;
    }

    private void caughtException(Exception ex) throws IOException {

    }

    private static String readErrorStream(HttpURLConnection connection) {
        String error = "";
        try {
            if (connection != null) {
                final InputStream errorStream = connection.getErrorStream();
                try {
                    if (errorStream != null) {
                        final byte[] data = readToArray(errorStream);
                        error = new String(data, "utf-8");
                    }
                } catch (IOException e) {
                    //Nothing to do
                }
                if (errorStream != null)
                    errorStream.close();
            }
        } catch (IOException e) {
            //Nothing to do;
        }
        return error;
    }

    public String createURL() {
        String result;
        List<NameValuePair> parameters = getParameters();
        final String url = getUrl();
        try {
            if (parameters != null && parameters.size() > 0) {
                result = String.format("%s?%s", url, format(parameters, "utf-8"));
            } else {
                result = url;
            }
        } catch (Exception ex) {
//            LogWriter.d(url);
            throw new IllegalArgumentException();
        }
        return result;
    }

    private static void writeLog(int status, String url, String error) {
//        LogWriter.d(String.format(Locale.US, "HTTP RESPONSE STATUS - %d;\n URL - %s; \n ERROR - %s", status, url, error));
    }

    private static void writeLog(int status, String url, Exception ex) {
//        LogWriter.e(String.format(Locale.US, "HTTP RESPONSE STATUS - %d;\n URL - %s; \n", status, url), ex);
    }

    protected static byte[] readToArray(InputStream in) throws IOException {
        if (in == null) return new byte[1];

        byte[] buf = new byte[1024];
        int count;
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        while ((count = in.read(buf)) > 0) {
            out.write(buf, 0, count);
        }
        return out.toByteArray();
    }

    protected boolean withCache() {
        return false;
    }

    protected int getCacheMaxAge() {
        return 0;
    }

    protected SSLContext getSSLContext() {
        return null;
    }

    protected boolean isAllowRedirect() {
        return false;
    }

    private static boolean isRedirect(int status) {
        return isSuccessful(status) && HttpURLConnection.HTTP_MULT_CHOICE <= status;
    }

    private static boolean isSuccessful(int status) {
        return status < HttpURLConnection.HTTP_BAD_REQUEST;
    }

    protected static String format(final List<? extends NameValuePair> parameters, final String encoding) {
        final StringBuilder result = new StringBuilder();
        for (final NameValuePair parameter : parameters) {
            final String encodedName = encode(parameter.getName(), encoding);
            final String value = parameter.getValue();
            final String encodedValue = value != null ? encode(value, encoding) : "";
            if (result.length() > 0)
                result.append(PARAMETER_SEPARATOR);
            result.append(encodedName);
            result.append(NAME_VALUE_SEPARATOR);
            result.append(encodedValue);
        }
        return result.toString();
    }

    private static String decode(final String content, final String encoding) {
        try {
            return URLDecoder.decode(content, encoding);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    private static String encode(final String content, final String encoding) {
        try {
            return URLEncoder.encode(content, encoding);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    public V getHPContext() {
        return _requestContext;
    }

//    protected void addBundleIDAlternative(Collection<NameValuePair> headers) {
//        addParameter(headers, "bundleID", My.Application.getPackageName());
//    }
//
//    protected void addBundleID(Collection<NameValuePair> headers) {
//        addParameter(headers, "BundleID", My.Application.getPackageName());
//    }
//
//    protected void addDeviceID(Collection<NameValuePair> headers) {
//        addParameter(headers, "DeviceID", HPDeviceID.readUUID());
//    }
//
//    protected void addHPDeviceID(Collection<NameValuePair> headers){
//        addParameter(headers, "HPDeviceID", HPDeviceID.readUUID());
//    }
//
//    protected void addDevice(Collection<NameValuePair> headers) {
//        addParameter(headers, "Device", Device.ANDROID);
//    }

    protected void addAuthorization(Collection<NameValuePair> headers, String authToken) {
        addParameter(headers, "Authorization", authToken);
    }

//    protected void addAppVersion(Collection<NameValuePair> headers) {
//        addParameter(headers, "AppVersion", My.Application.getVersion());
//    }

    protected void addUserID(Collection<NameValuePair> headers, String userid) {
        addParameter(headers, "UserId", userid);
    }

    protected void addEndpont(Collection<NameValuePair> headers, int endpoint) {
        addParameter(headers, "Endpoint", endpoint);
    }

    protected static void addParameter(Collection<NameValuePair> parameters, String name, Integer value) {
        if (value != null)
            addParameter(parameters, name, String.valueOf(value));
    }

    protected static void addParameter(Collection<NameValuePair> parameters, String name, String value) {
        if (value != null) {
            parameters.add(new BasicNameValuePair(name, value));
        }
    }

    protected static void addParameter(Map<String, String> parameters, String name, String value) {
        if (value != null) {
            parameters.put(name, value);
        }
    }

    protected static void addParameter(Map<String, String> parameters, String name, Integer value) {
        if (value != null) {
            parameters.put(name, String.valueOf(value));
        }
    }

    protected static List<NameValuePair> getParameters(Map<String, String> source) {

        List<NameValuePair> result = new ArrayList<NameValuePair>(source.size());
        for (Map.Entry<String, String> entry : source.entrySet()) {
            addParameter(result, entry.getKey(), entry.getValue());
        }
        return result;
    }
}
