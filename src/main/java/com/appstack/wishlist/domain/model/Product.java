package com.appstack.wishlist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@AllArgsConstructor
public class Product {
    @Field(targetType = FieldType.OBJECT_ID)
    private String productId;
    private Integer quantity;
}
