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
    private byte[] binaryBody = null;
    private boolean isBinaryBody = false;

    private RestResponse(int code, Map<String, String> headers) {
        this.code = code;
        this.headers = headers;
    }

    public RestResponse(int code, Map<String, String> headers, String body) {
        this(code, headers);
        this.body = body;
        this.isBinaryBody = false;
    }

    public RestResponse(int code, Map<String, String> headers, byte[] body) {
        this(code, headers);
        this.binaryBody = body;
        this.isBinaryBody = true;
    }

    public int getCode() {
        return code;
    }

    public boolean isBinaryBody() {
        return isBinaryBody;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public byte[] getBinaryBody() {
        return this.binaryBody;
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
