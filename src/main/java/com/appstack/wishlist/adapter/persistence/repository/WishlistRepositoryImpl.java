package com.appstack.wishlist.adapter.persistence.repository;

import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public class WishlistRepositoryImpl implements WishlistRepository {
}

interface SpringDataWishlistRepository extends MongoRepository<Wishlist, String> { }