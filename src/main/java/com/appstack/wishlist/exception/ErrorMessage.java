package com.appstack.wishlist.exception;

public enum ErrorMessage {
    WISHLIST_MAX_PRODUCTS("Wishlist cannot have more than 20 products"),
    WISHLIST_LIST_NOT_FOUND("Wishlist not found"),
    NO_WISHLIST_FOR_CUSTOMER("no wishlist found for this customer"),
    PRODUCT_ALREADY_EXISTS("Product already exists in wish list"),
    GENERIC_ERROR("Something went wrong. Please try again or contact support if the issue persists."),
    VALIDATION_FAILED("Validation failed")
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
