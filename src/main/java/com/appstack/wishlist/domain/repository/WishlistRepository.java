package com.appstack.wishlist.domain.repository;

import com.appstack.wishlist.domain.model.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository {
    Wishlist save(Wishlist wishlist);
    Optional<List<Wishlist>> findAllByCustomerId(String customerId);
    Optional<Wishlist> findById(String wishlistId);
    Wishlist findProductInWishlist(String wishlistId, String productId);
}
