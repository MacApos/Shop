package pl.coderslab.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderslab.entity.Category;
import pl.coderslab.util.CustomMapper;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CategoryDtoTest {
    @TestConfiguration
    static class CategoryDtoTestConfiguration {
        @Bean
        public CustomMapper customMapper() {
            return new CustomMapperImpl();
        }
    }

    @Autowired
    private CustomMapper customMapper;

    @Test
    public void givenCategory_whenMappingToDto_thenReturnDto() {
        Category parent = new Category("parent", null);
        CategoryDto categoryDto = new CategoryDto();
        customMapper.mapCategoryToDto(parent, categoryDto);
        assertThat(parent.getName()).isEqualTo(categoryDto.getName());
    }

}