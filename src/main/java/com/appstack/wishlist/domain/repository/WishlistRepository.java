package com.appstack.wishlist.domain.repository;

import com.appstack.wishlist.domain.model.Wishlist;

import java.util.Optional;

public interface WishlistRepository {
    Wishlist save(Wishlist wishlist);
    Optional<Wishlist> findByUserId(String userId);
}
