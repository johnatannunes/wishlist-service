package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.adapter.cache.ExternalProductCacheService;
import com.appstack.wishlist.domain.kafka.KafkaTopicKey;
import com.appstack.wishlist.domain.kafka.producer.ExternalProductCacheProducer;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import com.appstack.wishlist.exception.ExceptionMessage;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalProductServiceImplTest {

    @Mock
    private ExternalProductRepository externalProductRepository;

    @Mock
    private ExternalProductCacheProducer externalProductCacheProducer;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ExternalProductCacheService externalProductCacheService;

    @InjectMocks
    private ExternalProductServiceImpl externalProductServiceImpl;

    private final String productId = "123";
    private final ExternalProduct externalProduct = ExternalProduct.builder()
            .id(productId)
            .description("Test Product")
            .price(BigDecimal.valueOf(10.0))
            .observation("Available")
            .build();

    @Test
    void getProductsByIds() throws Exception {
        Set<String> productIds = Set.of(productId);
        ExternalProduct[] externalProducts = new ExternalProduct[]{externalProduct};
        when(externalProductRepository.getProductsByIds(anyList())).thenReturn(externalProducts);
        when(objectMapper.writeValueAsString(externalProducts)).thenReturn("json");
        doNothing().when(externalProductCacheProducer).sendMessage(anyString(), anyString());

        List<ExternalProduct> result = externalProductServiceImpl.getProductsByIds(productIds);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(externalProduct, result.getFirst());
        verify(externalProductRepository).getProductsByIds(anyList());
        verify(objectMapper).writeValueAsString(externalProducts);
        verify(externalProductCacheProducer).sendMessage(anyString(), anyString());
    }

    @Test
    void getProductsByIdsFallback() {
        Set<String> productIds = Set.of(productId);
        List<ExternalProduct> cachedProducts = Collections.singletonList(externalProduct);
        when(externalProductCacheService.getExternalProductCache(anyList())).thenReturn(cachedProducts);

        List<ExternalProduct> result = externalProductServiceImpl
                .getProductsByIdsFallback(productIds, new RuntimeException("Exception"));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(externalProduct, result.getFirst());
        verify(externalProductCacheService).getExternalProductCache(anyList());
    }

    @Test
    void getProductsByIdsException() throws JsonProcessingException {
        Set<String> productIds = Set.of(productId);
        when(externalProductRepository.getProductsByIds(anyList())).thenThrow(new RuntimeException("Exception"));

        PreconditionFailedException exception = assertThrows(
                PreconditionFailedException.class,
                () -> externalProductServiceImpl.getProductsByIds(productIds)
        );
        Assertions.assertEquals(ExceptionMessage.GENERIC_ERROR, exception.getMessage());
    }

    @Test
     void getUnavailableProduct() {
        ExternalProduct result = externalProductServiceImpl.getUnavailableProduct(productId);

        assertNotNull(result);
        Assertions.assertEquals(productId, result.getId());
        Assertions.assertEquals("Product Unavailable", result.getObservation());
    }

    @Test
     void sendMessageToExternalProductTopic() throws Exception {
        Set<String> productIds = Set.of(productId);
        ExternalProduct[] externalProducts = new ExternalProduct[]{externalProduct};
        when(externalProductRepository.getProductsByIds(anyList())).thenReturn(externalProducts);
        when(objectMapper.writeValueAsString(externalProducts)).thenReturn("json");
        doNothing().when(externalProductCacheProducer).sendMessage(anyString(), anyString());

        List<ExternalProduct> result = externalProductServiceImpl.getProductsByIds(productIds);

        Assertions.assertEquals(1, result.size());
        verify(externalProductCacheProducer).sendMessage(eq(KafkaTopicKey.PRODUCT_EXTERNAL_TOPIC.getTopic()),
                anyString());
    }
}
