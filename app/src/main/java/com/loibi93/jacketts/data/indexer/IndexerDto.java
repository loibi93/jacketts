package com.loibi93.jacketts.data.indexer;

import java.io.Serializable;

public class IndexerDto implements Serializable {
    private String name;
    private String id;

    public IndexerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
