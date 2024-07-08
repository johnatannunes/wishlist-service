package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.repository.ExternalProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExternalProductServiceImpl implements ExternalProductService {

    private static final String PRODUCT_UNAVAILABLE = "Product Unavailable";
    private final ExternalProductRepository externalProductRepository;

    @Override
    public List<ExternalProduct> getProductsByIds(Set<String> productIds) {
        return externalProductRepository.getProductsByIds(new ArrayList<>(productIds));
    }

    @Override
    public ExternalProduct getUnavailableProduct(String productId) {
        return ExternalProduct.builder()
                .id(productId)
                .observation(PRODUCT_UNAVAILABLE)
                .build();
    }
}
