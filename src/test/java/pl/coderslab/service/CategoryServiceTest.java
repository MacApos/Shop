package pl.coderslab.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.coderslab.common.CategoriesInitiation;
import pl.coderslab.entity.Category;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CategoryServiceTest extends CategoriesInitiation {
    private Set<Category> firstGenerationChildren;
    private Set<Category> secondGenerationChildren;

    @BeforeAll
    public void initiateCategoriesChildren() {
        firstGenerationChildren = new TreeSet<>(List.of(child3, others));
        secondGenerationChildren = new TreeSet<>(List.of(child4));
        parent2.setChildren(firstGenerationChildren);
        child3.setChildren(secondGenerationChildren);
    }

    @Test
    void givenParent_whenFindChildren_thanReturnCategoryWithChildrenInHierarchy() {
        Category category = categoryService.findChildren(parent2);
        Set<Category> children = category.getChildren();
        Category firstChild = children.stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);

        assertThat(category)
                .isNotNull()
                .isEqualTo(parent2)
                .extracting(Category::getChildren)
                .isEqualTo(firstGenerationChildren);

        assertThat(firstChild)
                .isNotNull()
                .isEqualTo(child3)
                .extracting(Category::getChildren)
                .isEqualTo(secondGenerationChildren);
    }

    @Test
    void givenParent_whenFindChildren_thanReturnCategoryWithChildrenWithOtherCategoryAsLast() {
        Set<Category> children = categoryService.findChildren(parent2).getChildren();
        assertThat(children).containsExactly(child3, others);
    }


}