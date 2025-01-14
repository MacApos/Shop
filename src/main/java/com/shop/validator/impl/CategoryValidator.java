package com.shop.validator.impl;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

public class CategoryValidator extends ValidatorService<Category> {
    private final CategoryService categoryService;

    public CategoryValidator(Category obj, CategoryService categoryService) {
        super(obj);
        this.categoryService = categoryService;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category byNameAndParent = categoryService.findByNameAndParent(obj);
        if(byNameAndParent!=null){
            errors.reject("parent", "Category "+"already.exists");
        }
    }
}
