package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WishlistRequest(@NotBlank() String userId,
                              @NotBlank() @Size(min = 1) String listName,
                              @NotNull() PrivacyStatusEnum privacyStatus) {}
