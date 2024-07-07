package com.appstack.wishlist.adapter.web.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(@NotBlank(message = "id must not be blank") String id) {}
