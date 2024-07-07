package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.adapter.mapper.ProductMapper;
import com.appstack.wishlist.adapter.mapper.WishlistMapper;
import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddItemToWishlistUseCase {

    private final WishlistService wishlistService;
    private final ProductMapper productMapper;
    private final WishlistMapper wishlistMapper;

    public WishlistResponse execute(String wishlistId, ProductRequest productRequest) {
       Product product = productMapper.toDomain(productRequest);
       Wishlist wishlist = wishlistService.addProductToWishlist(wishlistId, product);
       return wishlistMapper.toResponse(wishlist);
    }
}
