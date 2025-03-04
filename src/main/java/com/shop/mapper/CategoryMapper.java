package com.shop.mapper;

import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CategoryMapper implements GenericMapper<Category> {
    @Autowired
    private  CategoryRepository categoryRepository;

    @Override
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "hierarchy", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "parent", qualifiedByName = "categoryMapper")
    public abstract void update(Category source, @MappingTarget Category target);

    @Named("categoryMapper")
    public Category parentMapper(Category category) {
        if (category == null) {
            return null;
        }
        return categoryRepository.findById(category.getId()).orElse(null);
    }
}
