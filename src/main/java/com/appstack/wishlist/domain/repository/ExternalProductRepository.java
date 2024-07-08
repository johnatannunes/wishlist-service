package com.appstack.wishlist.domain.repository;

import com.appstack.wishlist.domain.model.ExternalProduct;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ExternalProductRepository {
    ExternalProduct[] getProductsByIds(List<String> ids) throws JsonProcessingException;
}
