package com.appstack.wishlist.domain.kafka.consumer;

import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class ExternalProductCacheConsumer {

    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "product-external-events", groupId = "wishlist-group")
    public void listen(String event) {
        ExternalProduct[] externalProducts = null;
        try {
            externalProducts = objectMapper.readValue(event, ExternalProduct[].class);
            setExternalProductCache(externalProducts);
        } catch (Exception e) {
            throw new PreconditionFailedException(e.getMessage());
        }
    }

    private void setExternalProductCache(ExternalProduct[] externalProducts){
        Cache cache = cacheManager.getCache("products_cache");
        if (!ObjectUtils.isEmpty(cache)) {
            for (ExternalProduct product : externalProducts) {
                cache.put(product.getId(), product);
            }
        }
    }
}
