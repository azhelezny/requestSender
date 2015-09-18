package rest;

import java.util.Map;

/**
 * @author Andrey Zhelezny
 *         Date: 8/31/15
 */
public class RestResponse {
    private int code;
    private Map<String, String> headers;
    private String body;

    public RestResponse(int code, Map<String, String> headers, String body) {
        this.code = code;
        this.headers = headers;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "code=" + code +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
