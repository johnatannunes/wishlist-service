package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import com.appstack.wishlist.exception.PreconditionFailedException;
import com.appstack.wishlist.exception.WishlistNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Optional<List<Wishlist>> getAllWishlistsByCustomerId(String customerId) {
        return wishlistRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Optional<Wishlist> getWishlistById(String id) {
        return wishlistRepository.findById(id);
    }

    @Override
    public Wishlist addItemToWishlist(String wishlistId, Product product) {
        var wishlistData = wishlistRepository.findById(wishlistId);
        var wishlist = wishlistData.orElseThrow(() -> new WishlistNotFoundException(wishlistId));

        if (ObjectUtils.isEmpty(wishlist.getProducts())) {
            wishlist.setProducts(List.of(product));
            return wishlistRepository.save(wishlist);
        }

        if (wishlist.getProducts().size() < 20) {
            wishlist.getProducts().add(product);
            return wishlistRepository.save(wishlist);
        }else{
            throw new PreconditionFailedException("Wishlist cannot have more than 20 products");
        }
    }

    @Override
    public void removeItemFromWishlist(String customerId, String itemId) {
    }
}
