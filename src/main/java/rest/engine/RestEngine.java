package rest.engine;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class RestEngine {
	private OkHttpClient httpClient = new OkHttpClient();

	public Response sendRequest(Request httpRequest) throws IOException {
		return httpClient.newCall(httpRequest).execute();
	}
}
