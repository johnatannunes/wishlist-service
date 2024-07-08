package com.appstack.wishlist.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExternalProduct {
    private String id;
    private String description;
    private BigDecimal price;
    private String observation;
}
