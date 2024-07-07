package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WishlistRequest(@NotBlank(message = "customerId must not be blank") String customerId,
                              @NotBlank(message = "listName must not be blank")
                              @Size(message = "List name requires at least one letter.", min = 1) String listName,
                              @NotNull(message = "Privacy status must not be null") PrivacyStatusEnum privacyStatus) {}
