package com.loibi93.jacketts.data.searchresult;

import java.util.List;

public class SearchResultDto {
    private List<SearchResultItemDto> Results;

    public SearchResultDto() {
    }

    public List<SearchResultItemDto> getResults() {
        return Results;
    }

    public void setResults(List<SearchResultItemDto> results) {
        this.Results = results;
    }
}
