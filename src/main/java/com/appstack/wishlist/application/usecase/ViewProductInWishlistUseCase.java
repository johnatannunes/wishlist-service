package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.adapter.mapper.WishlistDetailMapper;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistDetailResponse;
import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewProductInWishlistUseCase {

    private final WishlistService wishlistService;
    private final WishlistDetailMapper wishlistDetailMapper;

    public WishlistDetailResponse execute(String wishlistId, String productId) {
        return wishlistDetailMapper.toResponse(wishlistService.getProductInWishlist(wishlistId, productId));
    }
}
