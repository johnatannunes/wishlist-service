package com.appstack.wishlist.adapter.external.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalProductResponse {
    String id;
    String description;
    BigDecimal price;
    String observation;
}
