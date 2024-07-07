package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.adapter.mapper.WishlistMapper;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewSingleWishlistUseCase {

    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    public WishlistResponse execute(String wishlistId) {
        return wishlistMapper.toResponse(wishlistService.getWishlistById(wishlistId));
    }
}
