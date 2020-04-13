package com.loibi93.jacketts.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loibi93.jacketts.data.indexer.IndexerDto;
import com.loibi93.jacketts.data.searchresult.SearchResultDto;
import com.loibi93.jacketts.data.searchresult.SearchResultItemDto;
import com.loibi93.jacketts.data.http.HttpException;
import com.loibi93.jacketts.data.http.HttpService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackettService {
    private static final String INDEXER_ENDPOINT = "api/v2.0/indexers";
    private static final String SEARCH_ENDPOINT = "api/v2.0/indexers/all/results";

    private static JackettService INSTANCE = null;

    private Gson gson;

    private JackettService() {
        this.gson = new Gson();
    }

    public static JackettService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JackettService();
        }
        return INSTANCE;
    }

    public List<IndexerDto> loadIndexers() throws HttpException {
        Map<String, String> params = new HashMap<>();
        params.put("configured", "true");
        String response = HttpService.getInstance().get(INDEXER_ENDPOINT, params);

        if (response == null) {
            return Collections.emptyList();
        }

        return gson.fromJson(response, new TypeToken<Collection<IndexerDto>>() {
        }.getType());
    }

    public List<SearchResultItemDto> loadSearchResult(final String query, final String indexer) throws HttpException {
        Map<String, String> params = new HashMap<>();
        params.put("Tracker[]", indexer);
        params.put("Query", query);
        String response = HttpService.getInstance().get(SEARCH_ENDPOINT, params);

        if (response == null) {
            return Collections.emptyList();
        }

        return gson.fromJson(response, SearchResultDto.class).getResults();
    }
}
