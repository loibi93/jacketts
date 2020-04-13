package com.loibi93.jacketts.data.misc;

public class ServerConfig {
    private String api_key;

    private ServerConfig() {
    }

    public String getApiKey() {
        return api_key;
    }

    public void setApiKey(String api_key) {
        this.api_key = api_key;
    }
}
