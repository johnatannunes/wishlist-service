package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProductFromWishlistUseCase {

    private final WishlistService wishlistService;

    public void execute(String wishlistId, String productId) {
        wishlistService.removeProductFromWishlist(wishlistId, productId);
    }
}
