package com.appstack.wishlist.domain.kafka.consumer;

import com.appstack.wishlist.adapter.cache.ExternalProductCacheService;
import com.appstack.wishlist.common.logging.Logging;
import com.appstack.wishlist.config.MDCKey;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalProductCacheConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ExternalProductCacheConsumer.class);

    private final ObjectMapper objectMapper;
    private final ExternalProductCacheService externalProductCacheService;

    @KafkaListener(topics = "product-external-events", groupId = "wishlist-group")
    public void listen(String event) {
        ExternalProduct[] externalProducts;
        try {
            externalProducts = objectMapper.readValue(event, ExternalProduct[].class);
            externalProductCacheService.setExternalProductCache(externalProducts);

            Logging.logger(logger).mdcKey(MDCKey.PROCESS_ID)
                    .info("method: listen, externalProductsSize: {}", externalProducts.length);

        } catch (Exception e) {
            Logging.logger(logger).mdcKey(MDCKey.PROCESS_ID)
                    .error("method: listen", e.getMessage());
            throw new PreconditionFailedException(e.getMessage());
        }
    }

}
