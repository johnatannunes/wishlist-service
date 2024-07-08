package com.appstack.wishlist.adapter.mapper;

import com.appstack.wishlist.adapter.external.dto.ExternalProductResponse;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistDetailResponse;
import com.appstack.wishlist.domain.model.ExternalProduct;
import com.appstack.wishlist.domain.model.WishlistDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistDetailMapper {
    @Mapping(target = "products", source = "products")
    WishlistDetailResponse toResponse(WishlistDetail wishlist);

    @Mapping(source = "price", target = "price", qualifiedByName = "bigDecimalToString")
    ExternalProductResponse toExternalProductResponse(ExternalProduct product);

    @Mapping(source = "products", target = "products")
    List<ExternalProductResponse> toExternalProductResponseList(List<ExternalProduct> products);

    @Named("bigDecimalToString")
    static String bigDecimalToString(BigDecimal price) {
        return price != null ? price.toString() : null;
    }
}
