package com.loibi93.jacketts.data.requests;

import com.loibi93.jacketts.data.AsyncLiveData;
import com.loibi93.jacketts.data.JackettService;
import com.loibi93.jacketts.data.dto.Indexer;

import java.util.Collection;
import java.util.Collections;

public class IndexerRequest extends AsyncLiveData<Void, Collection<Indexer>> {
    public IndexerRequest() {
    }

    @Override
    protected Collection<Indexer> getData(Void input) {
        try {
            return JackettService.getInstance().loadIndexers();
        } catch (Exception e) {
            onError(e);
            return Collections.emptyList();
        }
    }
}
