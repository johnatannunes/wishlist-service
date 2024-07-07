package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;

import java.util.List;

public interface WishlistService {
    Wishlist createWishlist(Wishlist wishlist);
    List<Wishlist> getAllWishlistsByCustomerId(String customerId);
    Wishlist getWishlistById(String wishlistId);
    Wishlist addProductToWishlist(String wishlistId, Product product);
    void removeProductFromWishlist(String customerId, String productId);
}
