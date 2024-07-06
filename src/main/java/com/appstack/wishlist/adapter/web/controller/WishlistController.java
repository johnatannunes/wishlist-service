package com.appstack.wishlist.adapter.web.controller;

import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.application.usecase.AddItemToWishlistUseCase;
import com.appstack.wishlist.application.usecase.CreateWishlistUseCase;
import com.appstack.wishlist.application.usecase.RemoveItemFromWishlistUseCase;
import com.appstack.wishlist.application.usecase.ViewWishlistUseCase;
import com.appstack.wishlist.domain.model.Item;
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

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final CreateWishlistUseCase createWishlistUseCase;
    private final AddItemToWishlistUseCase addItemToWishlistUseCase;
    private final RemoveItemFromWishlistUseCase removeItemFromWishlistUseCase;
    private final ViewWishlistUseCase viewWishlistUseCase;

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

    @PostMapping("/{userId}/items")
    @Operation(summary = "Add Item to Wishlist", description = "Add an item to a user's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> addItemToWishlist(@PathVariable String userId,
                                                              @RequestBody @Valid Item item) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addItemToWishlistUseCase.execute(userId, item));
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    @Operation(summary = "Remove Item from Wishlist", description = "Remove an item from a user's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist or item not found")
    })
    public ResponseEntity<Void> removeItemFromWishlist(@PathVariable String userId, @PathVariable String itemId) {
        removeItemFromWishlistUseCase.execute(userId, itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "View Wishlist", description = "Retrieve the wishlist for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the wishlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    public ResponseEntity<WishlistResponse> viewWishlist(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewWishlistUseCase.execute(userId));
    }
}
