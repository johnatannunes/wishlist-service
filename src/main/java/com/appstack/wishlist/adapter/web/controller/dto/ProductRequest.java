package com.appstack.wishlist.adapter.web.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(@NotBlank() String productId) {}
