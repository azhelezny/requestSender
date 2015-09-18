package rest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrey Zhelezny
 *         Date: 8/31/15
 */
public class RestRequest {
    private String url;
    private RestMethod method;
    private Map<String, String> headers = new HashMap<String, String>();
    private String body;

    public RestRequest(String url, RestMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public RestMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String headerName, String headerValue) {
        this.headers.put(headerName, headerValue);
    }

    public void removeHeader(String header) {
        this.headers.remove(header);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RestRequest{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
