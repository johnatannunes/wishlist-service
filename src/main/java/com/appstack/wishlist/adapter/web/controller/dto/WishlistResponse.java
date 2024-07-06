package com.appstack.wishlist.adapter.web.controller.dto;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistResponse(String id,
                               String userId,
                               String listName,
                               PrivacyStatusEnum privacyStatus,
                               List<ItemRequest> items,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {
}
