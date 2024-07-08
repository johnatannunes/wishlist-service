package com.appstack.wishlist.domain.repository;

import com.appstack.wishlist.domain.model.ExternalProduct;

import java.util.List;

public interface ExternalProductRepository {
    List<ExternalProduct> getProductsByIds(List<String> ids);
}
