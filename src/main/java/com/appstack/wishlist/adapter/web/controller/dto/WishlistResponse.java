package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WishlistResponse(String id,
                               String customerId,
                               String listName,
                               PrivacyStatusEnum privacyStatus,
                               List<ProductResponse> products,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {
}
