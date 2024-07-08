package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.ExternalProduct;

import java.util.List;
import java.util.Set;

public interface ExternalProductService {
    List<ExternalProduct> getProductsByIds(Set<String> productIds);
    ExternalProduct getUnavailableProduct(String productId);
}
