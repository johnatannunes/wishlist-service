package com.appstack.wishlist.adapter.persistence.repository;

import com.appstack.wishlist.domain.model.Wishlist;
import com.appstack.wishlist.domain.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private final WishlistPersistenceRepository repository;

    @Override
    public Wishlist save(Wishlist wishlist) {
        return repository.save(wishlist);
    }

    @Override
    public Optional<Wishlist> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}

interface WishlistPersistenceRepository extends MongoRepository<Wishlist, String> {
    Optional<Wishlist> findByUserId(String userId);
}