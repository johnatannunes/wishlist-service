package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.ProductResponse;
import com.appstack.wishlist.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ItemMapper {
     Product toDomain(ProductRequest request);
     ProductResponse toResponse(Product product);
}
