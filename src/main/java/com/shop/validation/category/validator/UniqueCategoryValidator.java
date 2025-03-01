package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.CategoryExistsById;
import com.shop.validation.category.annotation.UniqueCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, Category> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryService.existsByNameAndParent(category);
    }
}