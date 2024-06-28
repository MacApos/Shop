package pl.coderslab.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.coderslab.dto.CategoryDto;
import pl.coderslab.entity.Category;


@Mapper(componentModel = "spring")
public  interface CustomMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCategoryToDto(Category category, @MappingTarget CategoryDto dto);
}
