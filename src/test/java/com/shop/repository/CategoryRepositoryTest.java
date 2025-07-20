package com.shop.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.shop.common.CategoriesInitiation;
import com.shop.model.Category;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
//@Import(RepositoryConfiguration.class)
public class CategoryRepositoryTest extends CategoriesInitiation {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenParent_whenFindAllChildrenByParent_thenReturnAllCategoriesWithGivenParent() {
        List<Category> children = categoryRepository.findAllByParent(parent1);
        assertThat(children)
                .isNotEmpty()
                .containsExactlyInAnyOrder(child1, child2)
                .extracting(Category::getParent)
                .allMatch(parent -> parent.equals(parent1));
    }

    @Test
    public void whenFindAllByParentIsNull_thenReturnAllCategoriesWithNullParent() {
        List<Category> grandparents = categoryRepository.findAllByParentIsNull();
        assertThat(grandparents)
                .isNotEmpty()
                .containsExactlyInAnyOrder(parent1, parent2)
                .extracting(Category::getParent)
                .allMatch(Objects::isNull);
    }

    @Test
    public void givenName_whenFindByName_thenReturnCategoryWithGivenName() {
        String name = child1.getName();
        Category category = categoryRepository.findByNameAndParent(name, parent1);
        assertThat(category)
                .isNotNull()
                .isEqualTo(child1)
                .extracting(Category::getName)
                .isEqualTo(name);
    }

    @Test
    public void givenNameAndParent_whenFindByNameAndParent_thenReturnCategoryWithGivenNameAndParent() {
        String name = child2.getName();
        Category category = categoryRepository.findByNameAndParent(name, parent1);
        assertThat(category)
                .isNotNull()
                .isEqualTo(child2)
                .extracting(Category::getName, Category::getParent)
                .containsExactly(name, parent1);
    }
}
