package pl.coderslab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderslab.dto.CategoryDto;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.impl.CategoryServiceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class CategoryServiceTest {
    @TestConfiguration
    static class CategoryServiceTestConfiguration {
        @Bean
        public CategoryService categoryService() {
            return new CategoryServiceImpl();
        }
    }

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    Category parent1;
    Category parent2;
    Category others;
    Category child1;
    Category child2;
    Category child3;

    @BeforeEach
    public void initCategoryRepository() {
        categoryService = new CategoryServiceImpl(categoryRepository);
        parent1 = new Category("parent1", null);
        parent2 = new Category("parent2", null);
        others = new Category("inne", null);

        child1 = new Category("child1", parent1);
        child2 = new Category("child2", parent1);
        child3 = new Category("child1", parent2);

        Mockito.when(categoryRepository.findAllByParentCategoryIsNull()).thenReturn(List.of(parent1, parent2, others));

        Mockito.when(categoryRepository.findAllChildrenByParentCategory(parent1)).thenReturn(List.of(child1, child2));
        Mockito.when(categoryRepository.findAllChildrenByParentCategory(parent2)).thenReturn(List.of(child3));
//        Mockito.when(categoryRepository.findAllChildrenByParentCategory(others)).thenReturn(List.of());
//        Mockito.when(categoryRepository.findAllChildrenByParentCategory(
//                Stream.of(child1, child2, child3).findAny().get())).thenReturn(List.of());

        Mockito.when(categoryRepository.findByNameAndParentCategory(child1.getName(), parent1)).thenReturn(child1);
        Mockito.when(categoryRepository.findByNameAndParentCategory(child3.getName(), parent1)).thenReturn(null);
    }

//    @Test
//    void getHierarchyMap() {
//        LinkedHashMap<Category, Object> children1 = new LinkedHashMap<>();
//        children1.put(child1, null);
//        children1.put(child2, null);
//
//        LinkedHashMap<Category, Object> children2 = new LinkedHashMap<>();
//        children2.put(child3, null);
//
//        LinkedHashMap<Category, Object> linkedHashMap = new LinkedHashMap<>();
//        linkedHashMap.put(parent1, children1);
//        linkedHashMap.put(parent2, children2);
//        linkedHashMap.put(others, null);
//
//        List<CategoryDto> hierarchyMap = categoryService.getHierarchyMap();
//        assertThat(hierarchyMap).containsExactlyEntriesOf(linkedHashMap);
//    }

    @Test
    void getParents() {
        parent2.setParentCategory(parent1);
        child1.setParentCategory(parent2);
        List<Category> firstParentLine = List.of(parent1);
        List<Category> secondParentLine = List.of(parent1, parent2);
        List<Category> childLine = List.of(parent1, parent2, child1);

        List<Category> parentsLine = categoryService.getParents(child1);
    }
//
//    @Test
//    void normalizeName() {
//    }
//
//    @Test
//    void splitPathAroundProduct() {
//    }
//
//    @Test
//    void findAllByParentCategory() {
//    }
//
//    @Test
//    void save() {
//    }
}