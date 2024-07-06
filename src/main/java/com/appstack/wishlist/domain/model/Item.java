package com.appstack.wishlist.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
public class Item {
    @Field(targetType = FieldType.OBJECT_ID)
    private String productId;
    private String productName;
    private Integer quantity;
}
