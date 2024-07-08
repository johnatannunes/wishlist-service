package com.appstack.wishlist.adapter.cache;

public enum CacheKey {
    EXTERNAL_PRODUCT_CACHE("external_product_cache");

    private final String key;

    CacheKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
