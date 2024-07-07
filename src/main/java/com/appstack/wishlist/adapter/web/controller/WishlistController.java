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
    @Operation(summary = "Create a new wishlist", description = "Creates a new wishlist for a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wishlist created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<WishlistResponse> createWishlist(@RequestBody @Valid WishlistRequest wishlistRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createWishlistUseCase.execute(wishlistRequest));
    }

    @PostMapping("/{wishlistId}/products")
    @Operation(summary = "Add a product to a wishlist", description = "Adds a product to an existing wishlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid product data or wishlist ID"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> addProductToWishlist(@PathVariable String wishlistId,
                                                              @RequestBody @Valid ProductRequest product) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addproductToWishlistUseCase.execute(wishlistId, product));
    }

    @DeleteMapping("/{wishlistId}/products/{productId}")
    @Operation(summary = "Remove a product from a wishlist", description = "Removes a product from an existing wishlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist or product not found")
    })
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable String wishlistId, @PathVariable String productId) {
        removeProductFromWishlistUseCase.execute(wishlistId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "View all wishlists for a customer", description = "Retrieves all wishlists for a specific customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved wishlists",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse[].class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found or no wishlists available")
    })
    public ResponseEntity<List<WishlistResponse>> viewWishlists(@PathVariable String customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewWishlistUseCase.execute(customerId));
    }

    @GetMapping("/{wishlistId}")
    @Operation(summary = "View a single wishlist", description = "Retrieves a single wishlist by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the wishlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> viewSingleWishlists(@PathVariable String wishlistId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewSingleWishlistUseCase.execute(wishlistId));
    }
}
