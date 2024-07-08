package com.appstack.wishlist.adapter.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExternalProductResponse {
    String id;
    String description;
    BigDecimal price;
    String observation;
}
