package com.shop.mapper;

import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = CategoryMapper.class)
public interface ProductMapper extends GenericMapper<Product> {

    @Mapping(target = "category", qualifiedByName = "categoryMapper")
    void update(Product source, @MappingTarget Product target);
}
