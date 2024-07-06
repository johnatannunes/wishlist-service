package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.adapter.mapper.WishlistMapper;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.model.Item;
import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddItemToWishlistUseCase {

    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    public WishlistResponse execute(String userId, Item item) {
       var wishlist = wishlistService.addItemToWishlist(userId, item);
       return wishlistMapper.toWishlistResponse(wishlist);
    }
}
