package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ItemRequest;
import com.appstack.wishlist.adapter.web.controller.dto.ItemResponse;
import com.appstack.wishlist.domain.model.Product;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-06T21:23:32-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Product toDomain(ItemRequest request) {
        if ( request == null ) {
            return null;
        }

        String productId = null;
        String productName = null;
        Integer quantity = null;

        productId = request.productId();
        productName = request.productName();
        quantity = request.quantity();

        Product product = new Product( productId, productName, quantity );

        return product;
    }

    @Override
    public ItemResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        String productId = null;
        String productName = null;
        Integer quantity = null;

        productId = product.getProductId();
        productName = product.getProductName();
        quantity = product.getQuantity();

        ItemResponse itemResponse = new ItemResponse( productId, productName, quantity );

        return itemResponse;
    }
}
