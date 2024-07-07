package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    Wishlist createWishlist(Wishlist wishlist);
    Optional<List<Wishlist>> getAllWishlistsByCustomerId(String customerId);
    Optional<Wishlist> getWishlistById(String wishlistId);
    Wishlist addItemToWishlist(String wishlistId, Product product);
    void removeItemFromWishlist(String customerId, String productId);
}
