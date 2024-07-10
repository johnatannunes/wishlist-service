package com.appstack.wishlist.cucumber.context;

import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WishlistFeatureContext {
    private WishlistResponse wishlistResponse;
    private String customerId;
}
