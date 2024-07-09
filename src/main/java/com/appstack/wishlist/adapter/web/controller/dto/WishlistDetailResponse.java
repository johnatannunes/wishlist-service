package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.adapter.external.dto.ExternalProductResponse;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WishlistDetailResponse(String id,
                                     String customerId,
                                     String listName,
                                     PrivacyStatusEnum privacyStatus,
                                     List<ExternalProductResponse> products,
                                     LocalDateTime createdAt,
                                     LocalDateTime updatedAt) {
}
