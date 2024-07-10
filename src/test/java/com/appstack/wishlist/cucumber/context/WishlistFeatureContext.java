package com.appstack.wishlist.cucumber.context;

import com.appstack.wishlist.adapter.web.controller.dto.WishlistDetailResponse;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WishlistFeatureContext {
    private WishlistResponse wishlistResponse;
    private WishlistDetailResponse wishlistDetailResponse;
    private String customerId;
}
