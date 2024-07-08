package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.kafka.producer.ExternalProductCacheProducer;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import com.appstack.wishlist.exception.ErrorMessage;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
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
    private final CacheManager cacheManager;
    private final ExternalProductCacheProducer externalProductCacheProducer;
    private final ObjectMapper objectMapper;

    @Override
    @Retry(name = "getProductsByIdsRetry", fallbackMethod = "getProductsByIdsFallback")
    public List<ExternalProduct> getProductsByIds(Set<String> productIds) {
        try {

            ExternalProduct[] externalProducts = externalProductRepository.getProductsByIds(new ArrayList<>(productIds));

            String json = objectMapper.writeValueAsString(externalProducts);
            externalProductCacheProducer.sendMessage("product-external-events", json);

            return Arrays.stream(externalProducts).toList();
        }catch (Exception e){
               throw new PreconditionFailedException(ErrorMessage.GENERIC_ERROR.getMessage());
        }
    }

    public List<ExternalProduct> getProductsByIdsFallback(Set<String> productIds, Throwable throwable) {
        List<ExternalProduct> productsExternalCache = new ArrayList<>();
        var cache = cacheManager.getCache("products_cache");
        if (cache != null) {
            productIds.forEach(productId -> {
                ExternalProduct cachedProduct = cache.get(productId, ExternalProduct.class);
                if (cachedProduct != null) {
                   productsExternalCache.add(cachedProduct);
                }
            });
        }

        return productsExternalCache;
    }

    @Override
    public ExternalProduct getUnavailableProduct(String productId) {
        return ExternalProduct.builder()
                .id(productId)
                .observation(PRODUCT_UNAVAILABLE)
                .build();
    }
}
