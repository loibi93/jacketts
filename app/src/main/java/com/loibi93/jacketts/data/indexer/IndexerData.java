package com.loibi93.jacketts.data.indexer;

import com.loibi93.jacketts.data.AsyncLiveData;
import com.loibi93.jacketts.data.JackettService;
import com.loibi93.jacketts.data.http.HttpException;

import java.util.List;

public class IndexerData extends AsyncLiveData<Void, List<IndexerDto>> {
    public IndexerData() {
    }

    @Override
    protected List<IndexerDto> loadData(Void input) throws HttpException {
        return JackettService.getInstance().loadIndexers();
    }
}
