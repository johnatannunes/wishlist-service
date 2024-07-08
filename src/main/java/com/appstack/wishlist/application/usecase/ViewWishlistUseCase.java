package com.appstack.wishlist.application.usecase;

import com.appstack.wishlist.adapter.mapper.WishlistDetailMapper;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistDetailResponse;
import com.appstack.wishlist.domain.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewWishlistUseCase {

    private final WishlistService wishlistService;
    private final WishlistDetailMapper wishlistDetailMapper;

    public List<WishlistDetailResponse> execute(String customerId) {
        return wishlistService.getAllWishlistsByCustomerId(customerId)
                .stream()
                .map(wishlistDetailMapper::toResponse)
                .toList();
    }
}
