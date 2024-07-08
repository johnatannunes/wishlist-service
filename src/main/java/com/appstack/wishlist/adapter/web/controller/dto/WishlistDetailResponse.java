package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.adapter.external.dto.ExternalProductResponse;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistDetailResponse(String id,
                                     String customerId,
                                     String listName,
                                     PrivacyStatusEnum privacyStatus,
                                     List<ExternalProductResponse> products,
                                     LocalDateTime createdAt,
                                     LocalDateTime updatedAt) {
}
