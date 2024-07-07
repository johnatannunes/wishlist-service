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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);
    private static final int MAXIMUM_QUANTITY_OF_PRODUCTS_ALLOWED = 20;
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
    public Wishlist addProductToWishlist(String wishlistId, Product product) {
        Wishlist wishlist = findWishlistById(wishlistId);
        addProductToWishlist(wishlist, product);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeProductFromWishlist(String wishlistId, String productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));
        wishlist.getProducts().remove(new Product(productId));
        wishlistRepository.save(wishlist);
    }

    private Wishlist findWishlistById(String wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistNotFoundException(wishlistId));
    }

    private void addProductToWishlist(Wishlist wishlist, Product product) {
        initializeProductListIfEmpty(wishlist);
        checkIfTheProductAlreadyExistsOnTheWishlist(wishlist, product);
        checkProductLimitInWishlist(wishlist);
        wishlist.getProducts().add(product);
    }

    private void initializeProductListIfEmpty(Wishlist wishlist) {
        if (ObjectUtils.isEmpty(wishlist.getProducts())) {
            wishlist.setProducts(new ArrayList<>());
        }
    }

    private void checkProductLimitInWishlist(Wishlist wishlist) {
        if (Objects.equals(wishlist.getProducts().size(), MAXIMUM_QUANTITY_OF_PRODUCTS_ALLOWED)) {
            throw new PreconditionFailedException("Wishlist cannot have more than 20 products");
        }
    }

    private void checkIfTheProductAlreadyExistsOnTheWishlist(Wishlist wishlist, Product product) {
        boolean productExists = wishlist.getProducts()
                .stream()
                .anyMatch(productInList
                        -> Objects.equals(product.getProductId(), productInList.getProductId()));

        if (productExists) {
            throw new PreconditionFailedException("Product already exists in wish list");
        }
    }
}
