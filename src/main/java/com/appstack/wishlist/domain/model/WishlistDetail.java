package com.appstack.wishlist.domain.model;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistDetail(String id,
                             String customerId,
                             String listName,
                             PrivacyStatusEnum privacyStatus,
                             List<ExternalProduct> products,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {

}
