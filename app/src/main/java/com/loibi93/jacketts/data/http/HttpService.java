package com.loibi93.jacketts.data.http;

import com.google.gson.Gson;
import com.loibi93.jacketts.data.misc.ServerConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.loibi93.jacketts.preferences.PreferenceHandler.getIntPreference;
import static com.loibi93.jacketts.preferences.PreferenceHandler.getStringPreference;
import static com.loibi93.jacketts.preferences.PreferenceKey.BASE_URL;
import static com.loibi93.jacketts.preferences.PreferenceKey.PASSWORD;
import static com.loibi93.jacketts.preferences.PreferenceKey.PORT;
import static com.loibi93.jacketts.preferences.PreferenceKey.PROTOCOL;

public class HttpService {
    private static final String LOGIN_ENDPOINT = "UI/Dashboard";
    private static final String SERVER_CONFIG_ENDPOINT = "api/v2.0/server/config";

    private static HttpService INSTANCE = null;

    private OkHttpClient httpClient;
    private PersistentCookieJar cookieJar;
    private Gson gson;
    private String apiKey;

    private HttpService() {
        this.cookieJar = new PersistentCookieJar();
        this.httpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }

    public static HttpService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpService();
        }

        return INSTANCE;
    }

    public String get(String endpoint, Map<String, String> queryParams) throws HttpException {
        HttpUrl.Builder urlBuilder = getBaseUrlBuilder()
                .addPathSegments(endpoint);

        if (queryParams != null) {
            for (Map.Entry<String, String> param : queryParams.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        HttpUrl url = urlBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = execute(request, true)) {
            ResponseBody body = response.body();

            if (body != null) {
                return body.string();
            }
        } catch (IOException e) {
            throw new HttpException(500, e);
        }

        return null;
    }

    private void loginIfNeeded() throws HttpException {
        if (cookieJar.isLoggedIn()) {
            return;
        }

        HttpUrl loginUrl = getBaseUrlBuilder()
                .addPathSegments(LOGIN_ENDPOINT)
                .build();

        RequestBody body = new FormBody.Builder()
                .addEncoded("password", getStringPreference(PASSWORD, "secret"))
                .build();

        Request request = new Request.Builder()
                .url(loginUrl)
                .post(body)
                .build();

        execute(request, false).close();

        String serverConfigJson = get(SERVER_CONFIG_ENDPOINT, null);
        ServerConfig serverConfig = gson.fromJson(serverConfigJson, ServerConfig.class);
        apiKey = serverConfig.getApiKey();
    }

    private Response execute(Request request, boolean loginRequired) throws HttpException {
        if (loginRequired) {
            loginIfNeeded();
        }

        HttpUrl.Builder urlBuilder = request.url().newBuilder();

        if (apiKey != null) {
            urlBuilder.addQueryParameter("apikey", apiKey);
        }

        request = request.newBuilder()
                .url(urlBuilder.build())
                .build();

        Response response;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new HttpException(500, e);
        }

        if (!response.isSuccessful()) {
            throw new HttpException(response.code());
        }

        return response;
    }

    private HttpUrl.Builder getBaseUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(getStringPreference(PROTOCOL, "https"))
                .host(getStringPreference(BASE_URL, "google.com"))
                .port(getIntPreference(PORT, 443));
    }
}
