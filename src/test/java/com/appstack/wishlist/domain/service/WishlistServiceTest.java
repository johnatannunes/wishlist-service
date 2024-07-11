package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.model.Product;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.model.WishlistDetail;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import com.appstack.wishlist.exception.ExceptionMessage;
import com.appstack.wishlist.exception.NotFoundException;
import com.appstack.wishlist.exception.PreconditionFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ExternalProductService externalProductService;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        wishlist = new Wishlist("668a3c139c5b9977c005c180", products);
        wishlist.setId("668a3c139c5b9977c005c183");
        wishlist.setListName("test");
    }

    @Test
    void createWishlist() {
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);
        Wishlist createdWishlist = wishlistService.createWishlist(wishlist);
        Assertions.assertEquals(wishlist, createdWishlist);
        verify(wishlistRepository).save(wishlist);
    }

    @Test
    void getAllWishlistsByCustomerId() {
        when(wishlistRepository.findAllByCustomerId(anyString())).thenReturn(Optional.of(List.of(wishlist)));
        List<WishlistDetail> result = wishlistService.getAllWishlistsByCustomerId("customer123");
        Assertions.assertEquals(1, result.size());
        verify(wishlistRepository).findAllByCustomerId("customer123");
    }

    @Test
    void getAllWishlistsByCustomerIdNotFound() {
        when(wishlistRepository.findAllByCustomerId(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.getAllWishlistsByCustomerId("customer123");
        });

        verify(wishlistRepository).findAllByCustomerId("customer123");
        Assertions.assertEquals(ExceptionMessage.NO_WISHLIST_FOR_CUSTOMER, exception.getMessage());
        verify(wishlistRepository).findAllByCustomerId("customer123");
    }

    @Test
    void getProductInWishlistSuccess() {
        String wishlistId = "wishlist123";
        String productId = "product123";
        wishlist = new Wishlist("customer123", List.of(new Product(productId)));
        WishlistDetail expectedDetail = new WishlistDetail(
                wishlist.getId(),
                wishlist.getCustomerId(),
                wishlist.getListName(),
                wishlist.getPrivacyStatus(),
                List.of(ExternalProduct.builder()
                                .id(productId)
                                .description("Product 123")
                                .price(BigDecimal.valueOf(10))
                                .observation("Product")
                        .build()),
                wishlist.getCreatedAt(),
                wishlist.getUpdatedAt()
        );

        when(wishlistRepository.findProductInWishlist(wishlistId, productId)).thenReturn(wishlist);
        WishlistDetail actualDetail = wishlistService.getProductInWishlist(wishlistId, productId);

        Assertions.assertEquals(expectedDetail.products().size(), actualDetail.products().size());
        verify(wishlistRepository).findProductInWishlist(wishlistId, productId);
    }

    @Test
    void getProductInWishlistNotFound() {
        String wishlistId = "wishlist123";
        String productId = "product123";

        when(wishlistRepository.findProductInWishlist(wishlistId, productId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.getProductInWishlist(wishlistId, productId);
        });

        Assertions.assertEquals(ExceptionMessage.PRODUCT_IN_WISHLIST_NOT_FOUND, exception.getMessage());
        verify(wishlistRepository).findProductInWishlist(wishlistId, productId);
    }

    @Test
    void removeProductFromWishlistSuccess() {
        String productId = "668a3c139c5b9977c005c184";
        Product product = new Product(productId);
        wishlist.getProducts().add(product);

        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        wishlistService.removeProductFromWishlist(wishlist.getId(), productId);

        Assertions.assertTrue(wishlist.getProducts().isEmpty());
        verify(wishlistRepository).save(wishlist);
    }

    @Test
    void removeProductFromWishlistWishlistNotFound() {
        String wishlistId = "wishlist123";
        String productId = "product123";

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.removeProductFromWishlist(wishlistId, productId);
        });

        Assertions.assertEquals(ExceptionMessage.WISHLIST_LIST_NOT_FOUND, exception.getMessage());
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    void getWishlistById() {
        final String productId = "668a3c139c5b9977c005c184";
        wishlist.getProducts().add(new Product(productId));
        ExternalProduct externalProduct = ExternalProduct.builder().id(productId).price(BigDecimal.valueOf(10)).build();
        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        when(externalProductService.getProductsByIds(Set.of(productId)))
                .thenReturn(List.of(externalProduct));

        WishlistDetail result = wishlistService.getWishlistById(wishlist.getId());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.products());
        Assertions.assertEquals(externalProduct.getId(), result.products().getFirst().getId());
    }

    @Test
    void addProductToWishlistSuccess() {
        Product product = new Product("668a3c139c5b9977c005c184");

        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        Wishlist result = wishlistService.addProductToWishlist(wishlist.getId(), product);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getProducts().contains(product));
        verify(wishlistRepository).findById(wishlist.getId());
        verify(wishlistRepository).save(wishlist);
    }

    @Test
    void addProductWhenAlreadyExistInWishlist() {
        Product product = new Product("668a3c139c5b9977c005c184");
        wishlist.getProducts().add(product);

        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        PreconditionFailedException exception = Assertions.assertThrows(PreconditionFailedException.class,
                () -> wishlistService.addProductToWishlist(wishlist.getId(), product)
        );

        Assertions.assertEquals(ExceptionMessage.PRODUCT_ALREADY_EXISTS, exception.getMessage());
        verify(wishlistRepository).findById(wishlist.getId());
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    void addProductWhenMaxExistInWishlist() {
        Product product = new Product("668a3c139c5b9977c005c184");

        int productMockId = 0;
        while (productMockId < 20) {
            productMockId++;
            wishlist.getProducts().add(new Product(String.valueOf(productMockId)));
        }

        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        PreconditionFailedException exception = Assertions.assertThrows(PreconditionFailedException.class,
                () -> wishlistService.addProductToWishlist(wishlist.getId(), product)
        );

        Assertions.assertEquals(ExceptionMessage.WISHLIST_MAX_PRODUCTS, exception.getMessage());
        verify(wishlistRepository).findById(wishlist.getId());
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    void AddProductToWishlistWishlistNotFound() {
        Product product = new Product("1234");
        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> wishlistService.addProductToWishlist(wishlist.getId(), product)
        );

        Assertions.assertEquals(ExceptionMessage.WISHLIST_LIST_NOT_FOUND, exception.getMessage());
        verify(wishlistRepository).findById(wishlist.getId());
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

}
