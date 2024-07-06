package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ItemRequest;
import com.appstack.wishlist.adapter.web.controller.dto.ItemResponse;
import com.appstack.wishlist.domain.model.Item;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-06T06:33:14-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toDomain(ItemRequest request) {
        if ( request == null ) {
            return null;
        }

        String productId = null;
        String productName = null;
        Integer quantity = null;

        productId = request.productId();
        productName = request.productName();
        quantity = request.quantity();

        Item item = new Item( productId, productName, quantity );

        return item;
    }

    @Override
    public ItemResponse toResponse(Item item) {
        if ( item == null ) {
            return null;
        }

        String productId = null;
        String productName = null;
        Integer quantity = null;

        productId = item.getProductId();
        productName = item.getProductName();
        quantity = item.getQuantity();

        ItemResponse itemResponse = new ItemResponse( productId, productName, quantity );

        return itemResponse;
    }
}
