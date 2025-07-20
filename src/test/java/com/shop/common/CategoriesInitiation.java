package com.shop.common;

import com.shop.service.CategoryService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.shop.model.Category;

import java.util.List;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(CategoryService.class)
public abstract class CategoriesInitiation {
    @Autowired
    protected CategoryService categoryService;

    protected Category parent1;
    protected Category parent2;
    protected Category others;
    protected Category child1;
    protected Category child2;
    protected Category child3;
    protected Category child4;
    protected List<Category> categories;

    @BeforeAll
    public void initiateCategories() {
        parent1 = new Category("parent1", null);
        parent2 = new Category("parent2", null);

        child1 = new Category("child1", parent1);
        child2 = new Category("child2", parent1);
        child3 = new Category("zzzchild3", parent2);
        child4 = new Category("child4", child3);
        others = new Category("inne", parent2);

        categories = List.of(parent1, parent2, child1, child2, child3, child4, others);
        categories.forEach(category -> categoryService.save(category));
    }

    @AfterAll
    public void deleteCategories() {
        categories.forEach(category -> categoryService.delete(category));
    }
}
