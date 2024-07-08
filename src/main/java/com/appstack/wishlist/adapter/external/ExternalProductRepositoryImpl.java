package com.appstack.wishlist.adapter.external;

import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExternalProductRepositoryImpl implements ExternalProductRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${external.api.products.url}")
    private String url;

    @Override
    public ExternalProduct[] getProductsByIds(List<String> ids) throws JsonProcessingException {
            String json = objectMapper.writeValueAsString(ids);
            return restTemplate.postForObject(url, json, ExternalProduct[].class);
    }
}
