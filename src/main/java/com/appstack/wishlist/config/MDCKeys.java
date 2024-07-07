package com.appstack.wishlist.config;

public enum MDCKeys {
    REQUEST_ID("requestId");

    private final String key;

    MDCKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
