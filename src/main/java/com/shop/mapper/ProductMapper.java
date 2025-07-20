package com.shop.mapper;

import com.shop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = CategoryMapper.class)
public interface ProductMapper extends GenericMapper<Product> {

    @Mapping(target = "category", qualifiedByName = "categoryMapper")
    void update(Product source, @MappingTarget Product target);
}
