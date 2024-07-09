package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.model.WishlistDetail;

import java.util.List;

public interface WishlistService {
    Wishlist createWishlist(Wishlist wishlist);
    List<WishlistDetail> getAllWishlistsByCustomerId(String customerId);
    WishlistDetail getWishlistById(String wishlistId);
    Wishlist addProductToWishlist(String wishlistId, Product product);
    void removeProductFromWishlist(String customerId, String productId);
    WishlistDetail getProductInWishlist(String wishlistId, String productId);
}
