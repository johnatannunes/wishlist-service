package com.appstack.wishlist.adapter.web.controller;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.application.usecase.*;
import com.appstack.wishlist.domain.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final CreateWishlistUseCase createWishlistUseCase;
    private final AddItemToWishlistUseCase addItemToWishlistUseCase;
    private final RemoveItemFromWishlistUseCase removeItemFromWishlistUseCase;
    private final ViewWishlistUseCase viewWishlistUseCase;
    private final ViewSingleWishlistUseCase viewSingleWishlistUseCase;

    @PostMapping
    @Operation(summary = "Creation Wishlist", description = "Creation wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> createWishlist(@RequestBody @Valid WishlistRequest wishlistRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createWishlistUseCase.execute(wishlistRequest));
    }

    @PostMapping("/{wishlistId}/products")
    @Operation(summary = "Add Item to Wishlist", description = "Add an item to a user's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> addItemToWishlist(@PathVariable String wishlistId,
                                                              @RequestBody @Valid ProductRequest product) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addItemToWishlistUseCase.execute(wishlistId, product));
    }

    @DeleteMapping("/{wishlistId}/products/{productId}")
    @Operation(summary = "Remove Item from Wishlist", description = "Remove an item from a user's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist or item not found")
    })
    public ResponseEntity<Void> removeItemFromWishlist(@PathVariable String wishlistId, @PathVariable String productId) {
        removeItemFromWishlistUseCase.execute(wishlistId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "View Wishlist", description = "Retrieve the wishlist for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the wishlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse[].class)) }),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<List<WishlistResponse>> viewWishlists(@PathVariable String customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewWishlistUseCase.execute(customerId));
    }

    @GetMapping("/{wishlistId}")
    @Operation(summary = "View Wishlist", description = "Retrieve the wishlist for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the wishlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> viewSingleWishlists(@PathVariable String wishlistId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewSingleWishlistUseCase.execute(wishlistId));
    }
}
