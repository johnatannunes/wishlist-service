package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.external.dto.ExternalProductResponse;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExternalProductMapper {
     ExternalProduct toDomain(ExternalProductResponse response);
}
