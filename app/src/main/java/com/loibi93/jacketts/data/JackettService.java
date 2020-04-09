package com.loibi93.jacketts.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loibi93.jacketts.data.dto.Indexer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import kotlin.collections.MapsKt;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.loibi93.jacketts.preferences.PreferenceHandler.getStringPreference;
import static com.loibi93.jacketts.preferences.PreferenceKey.API_KEY;
import static com.loibi93.jacketts.preferences.PreferenceKey.BASE_URL;
import static com.loibi93.jacketts.preferences.PreferenceKey.PROTOCOL;

public class JackettService {
    private static final String INDEXER_ENDPOINT = "api/v2.0/indexers";

    private static JackettService INSTANCE = null;

    private OkHttpClient httpClient;
    private Gson gson;
    private Map<String, String> params;

    private JackettService() {
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
        this.params = new HashMap<>();
    }

    public static JackettService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JackettService();
        }
        return INSTANCE;
    }

    public Collection<Indexer> loadIndexers() throws IOException {
        params.put("configured", "true");
        ResponseBody response = securedHttpCall(INDEXER_ENDPOINT, params).body();

        if (response == null) {
            return Collections.emptyList();
        }

        return gson.fromJson(response.string(), new TypeToken<Collection<Indexer>>(){}.getType());
    }

    private Response securedHttpCall(String endpoint) throws IOException {
        return securedHttpCall(endpoint, MapsKt.<String, String>emptyMap());
    }

    private Response securedHttpCall(String endpoint, Map<String, String> queryParameters) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(getStringPreference(PROTOCOL, "http"))
                .host(getStringPreference(BASE_URL, "localhost"))
                .addPathSegment(endpoint)
                .addQueryParameter("apikey", getStringPreference(API_KEY, "secret"));

        for (Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
            urlBuilder.addQueryParameter(queryParameter.getKey(), queryParameter.getValue());
        }
        queryParameters.clear();

        HttpUrl url = urlBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("HttpError: " + response.code());
        }

        return response;
    }
}
