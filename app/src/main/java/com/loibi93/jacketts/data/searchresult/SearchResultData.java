package com.loibi93.jacketts.data.searchresult;

import com.loibi93.jacketts.data.AsyncLiveData;
import com.loibi93.jacketts.data.JackettService;
import com.loibi93.jacketts.data.http.HttpException;

import java.util.List;

public class SearchResultData extends AsyncLiveData<String, List<SearchResultItemDto>> {
    private String indexerId;
    private String lastQuery;
    private List<SearchResultItemDto> lastResult;

    public SearchResultData(String indexerId) {
        this.indexerId = indexerId;
        this.lastQuery = null;
        this.lastResult = null;
    }

    @Override
    protected List<SearchResultItemDto> loadData(String query) throws HttpException {
        if (lastQuery != null && lastResult != null && lastQuery.contentEquals(query)) {
            return lastResult;
        }
        lastQuery = query;
        lastResult = JackettService.getInstance().loadSearchResult(query, indexerId);
        return lastResult;
    }
}
