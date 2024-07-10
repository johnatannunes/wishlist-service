package com.appstack.wishlist.exception;

public final class ExceptionMessage {

    private ExceptionMessage() {}

    public static final String WISHLIST_MAX_PRODUCTS = "Wishlist cannot have more than 20 products";
    public static final String WISHLIST_LIST_NOT_FOUND = "Wishlist not found";
    public static final String NO_WISHLIST_FOR_CUSTOMER = "No wishlist found for this customer";
    public static final String PRODUCT_ALREADY_EXISTS = "Product already exists in wishlist";
    public static final String PRODUCT_IN_WISHLIST_NOT_FOUND = "Wishlist or product not found";
    public static final String GENERIC_ERROR = "Something went wrong. Please try again or contact support if " +
            "the issue persists.";
}
