package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.web.controller.dto.ItemRequest;
import com.appstack.wishlist.adapter.web.controller.dto.ItemResponse;
import com.appstack.wishlist.domain.model.Item;
import org.mapstruct.Mapper;

@Mapper
public interface ItemMapper {
     Item toDomain(ItemRequest request);
     ItemResponse toResponse(Item item);
}
