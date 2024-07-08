package com.appstack.wishlist.domain.model;

import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "wishlists")
@CompoundIndex(name = "idx_customerId", def = "{'customerId' : 1}", sparse = true)
@EnableMongoAuditing
public class Wishlist {
    @Id
    private String id;
    @Field(targetType = FieldType.STRING)
    private String customerId;
    private String listName;
    private PrivacyStatusEnum privacyStatus;
    private List<Product> products;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Wishlist(String customerId, List<Product> products) {
        this.customerId = customerId;
        this.products = products;
    }
}
