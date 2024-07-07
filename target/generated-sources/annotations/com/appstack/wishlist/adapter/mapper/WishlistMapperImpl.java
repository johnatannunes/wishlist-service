package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-07T01:32:59-0300",
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
        String customerId = null;
        String listName = null;
        PrivacyStatusEnum privacyStatus = null;
        List<ProductRequest> products = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = wishlist.getId();
        customerId = wishlist.getCustomerId();
        listName = wishlist.getListName();
        privacyStatus = wishlist.getPrivacyStatus();
        products = productListToProductRequestList( wishlist.getProducts() );
        createdAt = wishlist.getCreatedAt();
        updatedAt = wishlist.getUpdatedAt();

        WishlistResponse wishlistResponse = new WishlistResponse( id, customerId, listName, privacyStatus, products, createdAt, updatedAt );

        return wishlistResponse;
    }

    @Override
    public Wishlist fromWishlistRequest(WishlistRequest request) {
        if ( request == null ) {
            return null;
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setCustomerId( request.customerId() );
        wishlist.setListName( request.listName() );
        wishlist.setPrivacyStatus( request.privacyStatus() );

        return wishlist;
    }

    protected ProductRequest productToProductRequest(Product product) {
        if ( product == null ) {
            return null;
        }

        String productId = null;
        Integer quantity = null;

        productId = product.getProductId();
        quantity = product.getQuantity();

        ProductRequest productRequest = new ProductRequest( productId, quantity );

        return productRequest;
    }

    protected List<ProductRequest> productListToProductRequestList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductRequest> list1 = new ArrayList<ProductRequest>( list.size() );
        for ( Product product : list ) {
            list1.add( productToProductRequest( product ) );
        }

        return list1;
    }
}
