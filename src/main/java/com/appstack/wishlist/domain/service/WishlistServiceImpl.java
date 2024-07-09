package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.common.logging.Logging;
import com.appstack.wishlist.config.MDCKey;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.model.WishlistDetail;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import com.appstack.wishlist.exception.ExceptionMessage;
import com.appstack.wishlist.exception.NotFoundException;
import com.appstack.wishlist.exception.PreconditionFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);
    private static final int MAXIMUM_QUANTITY_OF_PRODUCTS_ALLOWED = 20;
    private final WishlistRepository wishlistRepository;
    private final ExternalProductService externalProductService;

    @Override
    public Wishlist createWishlist(Wishlist wishlist) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: createWishlist, customer: {}", wishlist.getCustomerId());

        return  wishlistRepository.save(wishlist);
    }

    public List<WishlistDetail> getAllWishlistsByCustomerId(String customerId) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: getAllWishlistsByCustomerId, customer: {}", customerId);

        List<Wishlist> wishlists = wishlistRepository.findAllByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NO_WISHLIST_FOR_CUSTOMER));
        return wishlists.stream().map(this::convertWishlistToWishlistDetail).toList();
    }

    @Override
    public WishlistDetail getWishlistById(String wishlistId) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: getWishlistById, wishlistId: {}", wishlistId);

        Wishlist wishlist = findWishlistById(wishlistId);
        checkIfWishListIsNull(wishlist, ExceptionMessage.WISHLIST_LIST_NOT_FOUND);
        return convertWishlistToWishlistDetail(wishlist);
    }

    @Override
    public Wishlist addProductToWishlist(String wishlistId, Product product) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: addProductToWishlist, wishlistId: {}, productId: {}",
                         wishlistId, product.getId());

        Wishlist wishlist = findWishlistById(wishlistId);
        checkIfWishListIsNull(wishlist, ExceptionMessage.WISHLIST_LIST_NOT_FOUND);
        addProductToWishlist(wishlist, product);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeProductFromWishlist(String wishlistId, String productId) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: removeProductFromWishlist, wishlistId: {}, productId: {}",
                         wishlistId, productId);

        Wishlist wishlist = findWishlistById(wishlistId);
        checkIfWishListIsNull(wishlist, ExceptionMessage.WISHLIST_LIST_NOT_FOUND);
        wishlist.getProducts().remove(new Product(productId));
        wishlistRepository.save(wishlist);
    }

    @Override
    public WishlistDetail getProductInWishlist(String wishlistId, String productId) {

        Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                .info("method: getProductInWishlist, wishlistId: {}, productId: {}",
                         wishlistId, productId);

        Wishlist wishlist = wishlistRepository.findProductInWishlist(wishlistId, productId);
        checkIfWishListIsNull(wishlist, ExceptionMessage.PRODUCT_IN_WISHLIST_NOT_FOUND);
        return convertWishlistToWishlistDetail(wishlist);
    }

    private WishlistDetail convertWishlistToWishlistDetail(Wishlist wishlist){

        if(!ObjectUtils.isEmpty(wishlist.getProducts())){
            Set<String> productIds = extractProductIds(wishlist);
            List<ExternalProduct> externalProducts = fetchExternalProducts(productIds);
            List<ExternalProduct> externalProductsProcessed = processExternalProducts(productIds, externalProducts);
            return buildWishlistDetail(wishlist, externalProductsProcessed);
        }

        return buildWishlistDetail(wishlist, List.of());
    }

    private Set<String> extractProductIds(Wishlist wishlist) {
        return wishlist.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
    }

    private List<ExternalProduct> fetchExternalProducts(Set<String> productIds) {
        return externalProductService.getProductsByIds(productIds);
    }

    private List<ExternalProduct> processExternalProducts(Set<String> productIds,
                                                          List<ExternalProduct> externalProducts) {
        Map<String, ExternalProduct> productMap = externalProducts.stream()
                .collect(Collectors.toMap(ExternalProduct::getId, Function.identity(),
                        (existing, replacement) -> existing));

        return productIds.stream()
                .map(productId -> productMap.getOrDefault(productId,
                        externalProductService.getUnavailableProduct(productId)))
                .toList();
    }

    private WishlistDetail buildWishlistDetail(Wishlist wishlist, List<ExternalProduct> externalProducts) {
        return new WishlistDetail(wishlist.getId(),
                wishlist.getCustomerId(),
                wishlist.getListName(),
                wishlist.getPrivacyStatus(),
                externalProducts,
                wishlist.getCreatedAt(),
                wishlist.getUpdatedAt());
    }

    private Wishlist findWishlistById(String wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.WISHLIST_LIST_NOT_FOUND));
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
            throw new PreconditionFailedException(ExceptionMessage.WISHLIST_MAX_PRODUCTS);
        }
    }

    private void checkIfTheProductAlreadyExistsOnTheWishlist(Wishlist wishlist, Product product) {
        boolean productExists = wishlist.getProducts()
                .stream()
                .anyMatch(productInList
                        -> Objects.equals(product.getId(), productInList.getId()));

        if (productExists) {
            throw new PreconditionFailedException(ExceptionMessage.PRODUCT_ALREADY_EXISTS);
        }
    }

    private void checkIfWishListIsNull(Wishlist wishlist, final String ExceptionMessage) {
        if (ObjectUtils.isEmpty(wishlist)) {
            throw new NotFoundException(ExceptionMessage);
        }
    }
}
