package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.ProductResponse;
import com.appstack.wishlist.domain.model.Product;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-07T01:32:59-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Product toDomain(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        String productId = null;
        Integer quantity = null;

        productId = request.productId();
        quantity = request.quantity();

        Product product = new Product( productId, quantity );

        return product;
    }

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        String productId = null;
        Integer quantity = null;

        productId = product.getProductId();
        quantity = product.getQuantity();

        ProductResponse productResponse = new ProductResponse( productId, quantity );

        return productResponse;
    }
}
