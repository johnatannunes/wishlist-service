package com.appstack.wishlist.config;

public enum MDCKey {
    PROCESS_ID("processId"),
    REQUEST_ID("requestId");

    private final String key;

    MDCKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
