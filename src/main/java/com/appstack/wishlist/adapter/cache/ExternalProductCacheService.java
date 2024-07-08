package com.appstack.wishlist.adapter.cache;

import com.appstack.wishlist.common.logging.Logging;
import com.appstack.wishlist.config.MDCKey;
import com.appstack.wishlist.domain.model.ExternalProduct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ExternalProductCacheService.class);

    private final CacheManager cacheManager;

    public void setExternalProductCache(ExternalProduct[] externalProducts){
        Cache cache = cacheManager.getCache(CacheKey.EXTERNAL_PRODUCT_CACHE.getKey());
        if (!ObjectUtils.isEmpty(cache)) {
            for (ExternalProduct product : externalProducts) {
                cache.put(product.getId(), product);
            }
        }

        Logging.logger(logger).mdcKey(MDCKey.PROCESS_ID)
                .info("method: setExternalProductCache, externalProductsSize: {}", externalProducts.length);
    }

    public List<ExternalProduct> getExternalProductCache(final List<String> productIds) {
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

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: getExternalProductCache, productIds: {}", productIds.toString());

        return productsExternalCache;
    }
}
