package com.shop.mapper;

import com.shop.entity.Category;
import com.shop.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<Category> {
}
