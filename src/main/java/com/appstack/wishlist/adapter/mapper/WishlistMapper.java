package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ProductResponse;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    WishlistResponse toResponse(Wishlist wishlist);
    Wishlist toDomain(WishlistRequest request);

    @Mapping(source = "products", target = "products")
    List<ProductResponse> toProductResponseList(List<Product> products);

    @Mapping(source = "products", target = "products")
    List<Product> toProductList(List<ProductResponse> products);
}
