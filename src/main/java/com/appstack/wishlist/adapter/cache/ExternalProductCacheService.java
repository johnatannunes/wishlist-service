package com.appstack.wishlist.adapter.cache;

import com.appstack.wishlist.domain.model.ExternalProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExternalProductCacheService {

    private final CacheManager cacheManager;

    public void setExternalProductCache(ExternalProduct[] externalProducts){
        Cache cache = cacheManager.getCache(CacheKey.EXTERNAL_PRODUCT_CACHE.getKey());
        if (!ObjectUtils.isEmpty(cache)) {
            for (ExternalProduct product : externalProducts) {
                cache.put(product.getId(), product);
            }
        }
    }

    public List<ExternalProduct> getExternalProduct(final List<String> productIds) {
        List<com.appstack.wishlist.domain.model.ExternalProduct> productsExternalCache = new ArrayList<>();
        var cache = cacheManager.getCache(CacheKey.EXTERNAL_PRODUCT_CACHE.getKey());
        if (Objects.nonNull(cache)) {
            productIds.forEach(productId -> {
                com.appstack.wishlist.domain.model.ExternalProduct cachedProduct = cache.get(productId, com.appstack.wishlist.domain.model.ExternalProduct.class);
                if (cachedProduct != null) {
                    productsExternalCache.add(cachedProduct);
                }
            });
        }

        return productsExternalCache;
    }
}
