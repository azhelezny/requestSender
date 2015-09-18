package utils;

import rest.RestRequest;
import rest.RestResponse;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrey Zhelezny
 *         Date: 8/31/15
 */
public class RestUtils {
    static {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static RestResponse sendRequest(RestRequest request) throws IOException {
        // creation of request
        String url = request.getUrl();

        HttpURLConnection connection;
        if (url.startsWith("https")) {
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            connection = (HttpsURLConnection) new URL(url).openConnection();
        } else
            connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod(request.getMethod().toString());

        for (Map.Entry<String, String> header : request.getHeaders().entrySet())
            connection.setRequestProperty(header.getKey(), header.getValue());

        if (request.getBody() != null) {
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(request.getBody());
            wr.flush();
            wr.close();
        }

        // sending a request and getting a response
        int responseCode = connection.getResponseCode();

        Map<String, String> responseHeaders = new HashMap<String, String>();
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            StringBuilder headerValue = new StringBuilder();
            for (String value : header.getValue())
                headerValue.append(value).append(" ;");
            headerValue.deleteCharAt(headerValue.length() - 1).deleteCharAt(headerValue.length() - 1);
            responseHeaders.put(header.getKey(), headerValue.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((responseCode <= 300) ? connection.getInputStream() : connection.getErrorStream()));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        br.close();

        connection.disconnect();

        return new RestResponse(responseCode, responseHeaders, sb.toString());
    }
}
