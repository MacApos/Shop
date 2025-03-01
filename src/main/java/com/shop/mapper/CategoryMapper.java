package com.shop.mapper;

import com.shop.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper extends GenericMapper<Category> {
    @Override
    @Mapping(target = "parent", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "products", ignore = true)
    void update(Category source, @MappingTarget Category target);
}
