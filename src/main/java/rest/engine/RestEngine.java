package rest.engine;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

public class RestEngine {
    private OkHttpClient httpClient = new OkHttpClient();

    public void setProxy(Proxy proxy) {
        this.httpClient.setProxy(proxy);
    }

    public Response sendRequest(Request httpRequest) throws IOException {
        return httpClient.newCall(httpRequest).execute();
    }
}
