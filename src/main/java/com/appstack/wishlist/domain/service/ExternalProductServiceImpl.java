package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.adapter.cache.ExternalProductCacheService;
import com.appstack.wishlist.common.logging.Logging;
import com.appstack.wishlist.config.MDCKey;
import com.appstack.wishlist.domain.kafka.KafkaTopicKey;
import com.appstack.wishlist.domain.kafka.producer.ExternalProductCacheProducer;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import com.appstack.wishlist.exception.ExceptionMessage;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExternalProductServiceImpl implements ExternalProductService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalProductServiceImpl.class);

    private static final String PRODUCT_UNAVAILABLE = "Product Unavailable";
    private final ExternalProductRepository externalProductRepository;
    private final ExternalProductCacheProducer externalProductCacheProducer;
    private final ObjectMapper objectMapper;
    private final ExternalProductCacheService externalProductCacheService;

    @Override
    @Retry(name = "getProductsByIdsRetry", fallbackMethod = "getProductsByIdsFallback")
    public List<ExternalProduct> getProductsByIds(Set<String> productIds) {
        try {

            Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                    .info("method: {}, productIds: {}",
                            "getProductsByIds", productIds.toString());

            ExternalProduct[] externalProducts = externalProductRepository.getProductsByIds(new ArrayList<>(productIds));
            if (ObjectUtils.isEmpty(externalProducts)) {
                return new ArrayList<>();
            }
            sendMessageToExternalProductTopic(externalProducts);
            return Arrays.stream(externalProducts).toList();
        }catch (Exception e){

            Logging.logger(logger).mdcKey(MDCKey.PROCESS_ID)
                    .error("method: {}, productIds: {}",
                            "getProductsByIds", productIds.toString(), e.getMessage());

               throw new PreconditionFailedException(ExceptionMessage.GENERIC_ERROR);
        }
    }

    @Override
    public ExternalProduct getUnavailableProduct(String productId) {
        return ExternalProduct.builder()
                .id(productId)
                .observation(PRODUCT_UNAVAILABLE)
                .build();
    }

    public List<ExternalProduct> getProductsByIdsFallback(Set<String> productIds, Throwable throwable) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .error("method: getProductsByIdsFallback, productIds: {}", throwable);

        return externalProductCacheService.getExternalProductCache(productIds.stream().toList());
    }

    private void sendMessageToExternalProductTopic(final ExternalProduct[] externalProducts){
        try {
            String json = objectMapper.writeValueAsString(externalProducts);
            externalProductCacheProducer.sendMessage(KafkaTopicKey.PRODUCT_EXTERNAL_TOPIC.getTopic(), json);
        }catch (Exception e){
            Logging.logger(logger).mdcKey(MDCKey.PROCESS_ID)
                    .error("method: sendMessageToExternalProductTopic, productIds: {}", e);
        }
    }
}
