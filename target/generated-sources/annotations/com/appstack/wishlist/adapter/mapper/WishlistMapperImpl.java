package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ItemRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import com.appstack.wishlist.domain.model.Item;
import com.appstack.wishlist.domain.model.Wishlist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-06T06:33:14-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class WishlistMapperImpl implements WishlistMapper {

    @Override
    public WishlistResponse toWishlistResponse(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }

        String id = null;
        String userId = null;
        String listName = null;
        PrivacyStatusEnum privacyStatus = null;
        List<ItemRequest> items = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = wishlist.getId();
        userId = wishlist.getUserId();
        listName = wishlist.getListName();
        privacyStatus = wishlist.getPrivacyStatus();
        items = itemListToItemRequestList( wishlist.getItems() );
        createdAt = wishlist.getCreatedAt();
        updatedAt = wishlist.getUpdatedAt();

        WishlistResponse wishlistResponse = new WishlistResponse( id, userId, listName, privacyStatus, items, createdAt, updatedAt );

        return wishlistResponse;
    }

    @Override
    public Wishlist fromWishlistRequest(WishlistRequest request) {
        if ( request == null ) {
            return null;
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setUserId( request.userId() );
        wishlist.setListName( request.listName() );
        wishlist.setPrivacyStatus( request.privacyStatus() );

        return wishlist;
    }

    protected ItemRequest itemToItemRequest(Item item) {
        if ( item == null ) {
            return null;
        }

        String productId = null;
        String productName = null;
        Integer quantity = null;

        productId = item.getProductId();
        productName = item.getProductName();
        quantity = item.getQuantity();

        ItemRequest itemRequest = new ItemRequest( productId, productName, quantity );

        return itemRequest;
    }

    protected List<ItemRequest> itemListToItemRequestList(List<Item> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemRequest> list1 = new ArrayList<ItemRequest>( list.size() );
        for ( Item item : list ) {
            list1.add( itemToItemRequest( item ) );
        }

        return list1;
    }
}
