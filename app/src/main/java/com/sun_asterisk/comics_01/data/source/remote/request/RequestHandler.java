package com.sun_asterisk.comics_01.data.source.remote.request;

import com.sun_asterisk.comics_01.data.model.Cache;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 29/04/2019.
 * majanhqn@gmail.com
 */
public class RequestHandler {
    private static final int TIME_OUT = 15000;
    private static final int RESPONSE_OK = 200;
    private static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_PUT = "PUT";
    private static final String STR_FORMATTER = "UTF-8";
    private static final String TOKEN_EQUAL = "ToKen=";
    private static final String COOKIE = "Cookie";

    static String sendPost(String r_url, JSONObject postDataParams) throws Exception {
        URL url = new URL(r_url);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.setRequestMethod(METHOD_POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(outputStream, STR_FORMATTER));
        bufferedWriter.write(encodeParams(postDataParams));
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        BufferedReader br;

        if (connection.getResponseCode() == RESPONSE_OK) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    static String sendFollowWithToken(String r_url) throws Exception {
        URL url = new URL(r_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String token = TOKEN_EQUAL + Cache.getInstance().getToken();
        connection.setRequestMethod(METHOD_POST);
        connection.setRequestProperty(COOKIE, token);
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader br;

        if (connection.getResponseCode() == RESPONSE_OK) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    static String sendFollowWithToken(String r_url, JSONObject jsonObject, String method) throws Exception {
        URL url = new URL(r_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String token = TOKEN_EQUAL + Cache.getInstance().getToken();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty(COOKIE, token);
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        if (jsonObject != null) {
            OutputStream os = connection.getOutputStream();
            os.write(jsonObject.toString().getBytes(STR_FORMATTER));
            os.close();
        }
        BufferedReader br;

        if (connection.getResponseCode() == RESPONSE_OK) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    static String sendPutWithToken(String r_url, JSONObject jsonObject) throws Exception {
        URL url = new URL(r_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String cookie = TOKEN_EQUAL + Cache.getInstance().getToken();
        connection.setRequestMethod(METHOD_PUT);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty(COOKIE, cookie);
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.connect();
        if (jsonObject != null) {
            OutputStream os = connection.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.close();
        }
        BufferedReader br;

        if (connection.getResponseCode() == RESPONSE_OK) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    static String sendGet(String url, String token) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        String cookie = TOKEN_EQUAL + token;
        connection.setRequestMethod(METHOD_GET);
        connection.setRequestProperty(COOKIE, cookie);
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.connect();

        BufferedReader br;

        if (connection.getResponseCode() == RESPONSE_OK) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private static String encodeParams(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, STR_FORMATTER));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), STR_FORMATTER));
        }
        return result.toString();
    }

    public static String sendGetFollow(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        String cookie = TOKEN_EQUAL + Cache.getInstance().getToken();
        connection.setRequestMethod(METHOD_GET);
        connection.setRequestProperty(COOKIE, cookie);
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.connect();

        BufferedReader br;

        if (connection.getResponseCode() == RESPONSE_OK) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
