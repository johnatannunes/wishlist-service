package com.appstack.wishlist.domain.service;

import com.appstack.wishlist.domain.model.Item;
import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist createWishlist(Wishlist wishlist) {
        logger.debug("Executando o caso de uso para adicionar o item {} à wishlist {}", wishlist.getUserId(),
                wishlist.getListName());
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Optional<Wishlist> getWishlistByUserId(String userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public Wishlist addItemToWishlist(String userId, Item item) {
        return null;
    }

    @Override
    public void removeItemFromWishlist(String userId, String itemId) {
    }
}
