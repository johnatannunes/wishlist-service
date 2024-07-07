package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.adapter.mapper.WishlistMapper;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewWishlistUseCase {

    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    public List<WishlistResponse> execute(String customerId) {
        return wishlistService.getAllWishlistsByCustomerId(customerId)
                .map(wishlists -> wishlists.stream()
                        .map(wishlistMapper::toWishlistResponse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
