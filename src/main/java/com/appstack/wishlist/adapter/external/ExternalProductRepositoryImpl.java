package com.appstack.wishlist.adapter.external;

import com.appstack.wishlist.adapter.external.dto.ExternalProductResponse;
import com.appstack.wishlist.adapter.mapper.ExternalProductMapper;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ExternalProductRepositoryImpl implements ExternalProductRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ExternalProductMapper externalProductMapper;

    @Value("${external.api.products.url}")
    private String url;

    @Override
    public List<ExternalProduct> getProductsByIds(List<String> ids) {
        try {
            String json = objectMapper.writeValueAsString(ids);
            ExternalProductResponse[] externalProducts =
                    restTemplate.postForObject(url, json, ExternalProductResponse[].class);
            return Arrays.stream(externalProducts)
                    .map(externalProductMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
