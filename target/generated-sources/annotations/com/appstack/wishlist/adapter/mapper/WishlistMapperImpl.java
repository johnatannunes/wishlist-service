package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ProductResponse;
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
    date = "2024-07-08T02:51:45-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class WishlistMapperImpl implements WishlistMapper {

    @Override
    public WishlistResponse toResponse(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }

        String id = null;
        String customerId = null;
        String listName = null;
        PrivacyStatusEnum privacyStatus = null;
        List<ProductResponse> products = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = wishlist.getId();
        customerId = wishlist.getCustomerId();
        listName = wishlist.getListName();
        privacyStatus = wishlist.getPrivacyStatus();
        products = toProductResponseList( wishlist.getProducts() );
        createdAt = wishlist.getCreatedAt();
        updatedAt = wishlist.getUpdatedAt();

        WishlistResponse wishlistResponse = new WishlistResponse( id, customerId, listName, privacyStatus, products, createdAt, updatedAt );

        return wishlistResponse;
    }

    @Override
    public Wishlist toDomain(WishlistRequest request) {
        if ( request == null ) {
            return null;
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setCustomerId( request.customerId() );
        wishlist.setListName( request.listName() );
        wishlist.setPrivacyStatus( request.privacyStatus() );

        return wishlist;
    }

    @Override
    public List<ProductResponse> toProductResponseList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( products.size() );
        for ( Product product : products ) {
            list.add( productToProductResponse( product ) );
        }

        return list;
    }

    @Override
    public List<Product> toProductList(List<ProductResponse> products) {
        if ( products == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( products.size() );
        for ( ProductResponse productResponse : products ) {
            list.add( productResponseToProduct( productResponse ) );
        }

        return list;
    }

    protected ProductResponse productToProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        String id = null;

        id = product.getId();

        ProductResponse productResponse = new ProductResponse( id );

        return productResponse;
    }

    protected Product productResponseToProduct(ProductResponse productResponse) {
        if ( productResponse == null ) {
            return null;
        }

        String id = null;

        id = productResponse.id();

        Product product = new Product( id );

        return product;
    }
}
