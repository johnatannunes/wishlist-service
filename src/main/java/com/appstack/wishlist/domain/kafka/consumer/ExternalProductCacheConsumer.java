package com.appstack.wishlist.domain.kafka.consumer;

import com.appstack.wishlist.adapter.cache.ExternalProductCacheService;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalProductCacheConsumer {

    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;
    private final ExternalProductCacheService externalProductCacheService;

    @KafkaListener(topics = "product-external-events", groupId = "wishlist-group")
    public void listen(String event) {
        ExternalProduct[] externalProducts;
        try {
            externalProducts = objectMapper.readValue(event, ExternalProduct[].class);
            externalProductCacheService.setExternalProductCache(externalProducts);
        } catch (Exception e) {
            throw new PreconditionFailedException(e.getMessage());
        }
    }

}
