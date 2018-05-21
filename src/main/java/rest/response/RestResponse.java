package rest.response;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestResponse {

    private int code;
    private Map<String, String> headers;
    private ResponseBody body;

    public RestResponse(Response httpResponse) throws IOException {
        code = httpResponse.code();
        body = httpResponse.body();

        for (String headerName : httpResponse.headers().names())
            this.setHeader(headerName, httpResponse.header(headerName));
    }

    public int getCode() {
        return code;
    }

    private void setHeader(String name, String value) {
        if (headers == null)
            headers = new HashMap<>();
        headers.put(name, value);
    }

    public String getBody() {
        try {
            return body.string();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to convert response body to string " + e.getMessage());
            return "";
        }
    }

    public byte[] getBinaryBody() {
        try {
            return body.bytes();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to get binary body from response");
            return null;
        }
    }

    public String getHeadersAsString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> header : this.headers.entrySet()) {
            sb.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
