package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.adapter.cache.ExternalProductCacheService;
import com.appstack.wishlist.domain.kafka.KafkaTopicKey;
import com.appstack.wishlist.domain.kafka.producer.ExternalProductCacheProducer;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import com.appstack.wishlist.exception.ErrorMessage;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExternalProductServiceImpl implements ExternalProductService {

    private static final String PRODUCT_UNAVAILABLE = "Product Unavailable";
    private final ExternalProductRepository externalProductRepository;
    private final ExternalProductCacheProducer externalProductCacheProducer;
    private final ObjectMapper objectMapper;
    private final ExternalProductCacheService externalProductCacheService;

    @Override
    @Retry(name = "getProductsByIdsRetry", fallbackMethod = "getProductsByIdsFallback")
    public List<ExternalProduct> getProductsByIds(Set<String> productIds) {
        try {
            ExternalProduct[] externalProducts = externalProductRepository.getProductsByIds(new ArrayList<>(productIds));
            sendMessageToExternalProductTopic(externalProducts);
            return Arrays.stream(externalProducts).toList();
        }catch (Exception e){
               throw new PreconditionFailedException(ErrorMessage.GENERIC_ERROR.getMessage());
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
        return externalProductCacheService.getExternalProduct(productIds.stream().toList());
    }

    private void sendMessageToExternalProductTopic(final ExternalProduct[] externalProducts){
        try {
            String json = objectMapper.writeValueAsString(externalProducts);
            externalProductCacheProducer.sendMessage(KafkaTopicKey.PRODUCT_EXTERNAL_TOPIC.getTopic(), json);
        }catch (Exception ignored){
            System.out.println("ss");
        }
    }
}
