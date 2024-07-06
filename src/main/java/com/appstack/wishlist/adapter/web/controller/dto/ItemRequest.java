package com.appstack.wishlist.adapter.web.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ItemRequest(@NotBlank() String productId,
                          @NotBlank() String productName,
                          @Min(1) Integer quantity) {
}
