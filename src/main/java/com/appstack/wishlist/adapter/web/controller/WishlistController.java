package com.appstack.wishlist.adapter.web.controller;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.application.usecase.*;
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
    private final AddProductToWishlistUseCase addproductToWishlistUseCase;
    private final RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;
    private final ViewWishlistUseCase viewWishlistUseCase;
    private final ViewSingleWishlistUseCase viewSingleWishlistUseCase;

    @PostMapping
    @Operation(summary = "Creation Wishlist", description = "Creation wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product added successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> createWishlist(@RequestBody @Valid WishlistRequest wishlistRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createWishlistUseCase.execute(wishlistRequest));
    }

    @PostMapping("/{wishlistId}/products")
    @Operation(summary = "Add product to Wishlist", description = "Add an product to a customer's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product added successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> addProductToWishlist(@PathVariable String wishlistId,
                                                              @RequestBody @Valid ProductRequest product) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addproductToWishlistUseCase.execute(wishlistId, product));
    }

    @DeleteMapping("/{wishlistId}/products/{productId}")
    @Operation(summary = "Remove product from Wishlist", description = "Remove an product from a customer's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product removed successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist or product not found")
    })
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable String wishlistId, @PathVariable String productId) {
        removeProductFromWishlistUseCase.execute(wishlistId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "View Wishlist", description = "Retrieve the wishlist for a specific customer")
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
    @Operation(summary = "View Single Wishlist", description = "Retrieve the wishlist for a specific customer")
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
