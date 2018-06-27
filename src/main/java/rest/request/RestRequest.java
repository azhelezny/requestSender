package rest.request;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import rest.engine.RestEngine;
import rest.enums.RestMethod;

import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class RestRequest {
    private String url;
    private RestMethod method;
    private Map<String, String> headersMap = null;
    private String body;
    private byte[] binaryBody;
    private Proxy proxy;

    public void setProxy(String address, int port) {
        this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, port));
    }

    public RestRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public RestRequest setBinaryBody(byte[] body) {
        this.binaryBody = body;
        return this;
    }

    public RestRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public RestRequest setMethod(RestMethod method) {
        this.method = method;
        return this;
    }

    public RestRequest setHeaders(String headers) {
        headersMap = new HashMap<>();
        if (headers == null || headers.isEmpty())
            return this;
        String[] pairs = headers.split("\n");
        for (String s : pairs) {
            if (!s.contains(":")) {
                JOptionPane.showMessageDialog(null, "Unable to process headers " + s);
                return this;
            }
            try {
                this.headersMap.put(new String(beforeSeparator(s).getBytes("utf-8")), new String(afterSeparator(s).getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "Unable to process headers [" + s + "] because of " + e.getMessage());
            }
        }
        return this;
    }

    private String beforeSeparator(String s) {
        int point = s.indexOf(":");
        return s.substring(0, point).trim();
    }

    private String afterSeparator(String s) {
        int point = s.indexOf(":");
        return s.substring(point + 1).trim();
    }

    public String getUrl() {
        return url;
    }

    public RestMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }


    public String getBody() {
        return body;
    }

    public byte[] getBinaryBody() {
        return binaryBody;
    }

    public Response executeBasic(boolean isBinary) throws IOException {
        RestEngine engine = new RestEngine();
        if (proxy != null)
            engine.setProxy(this.proxy);
        return engine.sendRequest(this.getOkHttpRequest(isBinary));
    }

    private boolean isBodyEmpty() {
        return (getBody() == null || getBody().isEmpty())
                && (getBinaryBody() == null || getBinaryBody().length == 0);
    }

    private RequestBody getRequestBody(boolean isBinary) {
        if (getMethod().equals(RestMethod.GET))
            return null;
        if (getMethod().equals(RestMethod.DELETE) && isBodyEmpty())
            return null;
        String contentType = headersMap.get("Content-Type");
        if (contentType == null)
            throw new RuntimeException("Content type required!");
        if (isBinary)
            return RequestBody.create(MediaType.parse(contentType), getBinaryBody());
        else
            return RequestBody.create(MediaType.parse(contentType), getBody());
    }

    public Request getOkHttpRequest(boolean isBinary) {
        RequestBody requestBody = getRequestBody(isBinary);
        Request.Builder builder = new Request.Builder();
        builder.url(this.getUrl());
        if (getMethod().equals(RestMethod.GET))
            builder.get();
        else if (getMethod().equals(RestMethod.DELETE)) {
            if (requestBody == null)
                builder.delete();
        } else
            builder.method(this.getMethod().toString(), requestBody);
        for (Map.Entry<String, String> header : this.getHeadersMap().entrySet())
            builder.addHeader(header.getKey(), header.getValue());
        return builder.build();
    }
}