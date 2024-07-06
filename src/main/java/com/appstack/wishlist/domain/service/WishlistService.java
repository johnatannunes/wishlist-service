package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Item;
import com.appstack.wishlist.domain.model.Wishlist;

import java.util.Optional;

public interface WishlistService {
    Wishlist createWishlist(Wishlist wishlist);
    Optional<Wishlist> getWishlistByUserId(String userId);
    Wishlist addItemToWishlist(String userId, Item item);
    void removeItemFromWishlist(String userId, String itemId);
}
