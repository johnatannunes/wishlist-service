package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ItemRequest;
import com.appstack.wishlist.adapter.web.controller.dto.ItemResponse;
import com.appstack.wishlist.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ItemMapper {
     Product toDomain(ItemRequest request);
     ItemResponse toResponse(Product product);
}
