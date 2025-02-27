package com.shop.mapper;

import com.shop.entity.Cart;
import com.shop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends GenericMapper<Cart> {
}
