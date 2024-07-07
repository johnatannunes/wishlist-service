package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import com.appstack.wishlist.exception.ErrorMessage;
import com.appstack.wishlist.exception.NotFoundException;
import com.appstack.wishlist.exception.PreconditionFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<Wishlist> getAllWishlistsByCustomerId(String customerId) {
        return wishlistRepository.findAllByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.WISHLIST_LIST_NOT_FOUND.getMessage()));
    }

    @Override
    public Wishlist getWishlistById(String wishlistId) {
        return findWishlistById(wishlistId);
    }

    @Override
    public Wishlist addProductToWishlist(String wishlistId, Product product) {
        Wishlist wishlist = findWishlistById(wishlistId);
        addProductToWishlist(wishlist, product);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeProductFromWishlist(String wishlistId, String productId) {
        Wishlist wishlist = findWishlistById(wishlistId);
        wishlist.getProducts().remove(new Product(productId));
        wishlistRepository.save(wishlist);
    }

    private Wishlist findWishlistById(String wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.WISHLIST_LIST_NOT_FOUND.getMessage()));
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
            throw new PreconditionFailedException(ErrorMessage.WISHLIST_MAX_PRODUCTS.getMessage());
        }
    }

    private void checkIfTheProductAlreadyExistsOnTheWishlist(Wishlist wishlist, Product product) {
        boolean productExists = wishlist.getProducts()
                .stream()
                .anyMatch(productInList
                        -> Objects.equals(product.getId(), productInList.getId()));

        if (productExists) {
            throw new PreconditionFailedException(ErrorMessage.PRODUCT_ALREADY_EXISTS.getMessage());
        }
    }
}
