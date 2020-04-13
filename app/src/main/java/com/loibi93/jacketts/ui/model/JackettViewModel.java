package com.loibi93.jacketts.ui.model;

import androidx.lifecycle.ViewModel;

import com.loibi93.jacketts.data.indexer.IndexerData;
import com.loibi93.jacketts.data.indexer.IndexerDto;
import com.loibi93.jacketts.data.searchresult.SearchResultData;
import com.loibi93.jacketts.ui.view.SearchBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackettViewModel extends ViewModel implements SearchBar.OnSearchListener {
    private IndexerData indexerData = new IndexerData();
    private Map<String, SearchResultData> resultDataMap = new HashMap<>();

    public void initResultData(List<IndexerDto> indexers) {
        for (IndexerDto dto : indexers) {
            if (resultDataMap.get(dto.getId()) == null) {
                resultDataMap.put(dto.getId(), new SearchResultData(dto.getId()));
            }
        }
    }

    public IndexerData getIndexers() {
        return indexerData;
    }

    public SearchResultData getResultData(String indexerId) {
        return resultDataMap.get(indexerId);
    }

    @Override
    public void onSearch(String query) {
        for (SearchResultData resultData : resultDataMap.values()) {
            resultData.update(query);
        }
    }
}
