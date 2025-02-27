package com.shop.mapper;

import com.shop.entity.CartItem;
import com.shop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends GenericMapper<CartItem> {
}
