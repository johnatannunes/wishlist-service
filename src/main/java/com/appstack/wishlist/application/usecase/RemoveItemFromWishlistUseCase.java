package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveItemFromWishlistUseCase {

    private final WishlistService wishlistService;

    public void execute(String customerId, String itemId) {
        wishlistService.removeItemFromWishlist(customerId, itemId);
    }
}
