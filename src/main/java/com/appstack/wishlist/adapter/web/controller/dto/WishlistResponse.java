package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistResponse(String id,
                               String customerId,
                               String listName,
                               PrivacyStatusEnum privacyStatus,
                               List<ProductResponse> products,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {
}
