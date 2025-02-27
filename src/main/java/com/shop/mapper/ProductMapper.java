package com.shop.mapper;

import com.shop.entity.Product;
import com.shop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<Product> {
}
