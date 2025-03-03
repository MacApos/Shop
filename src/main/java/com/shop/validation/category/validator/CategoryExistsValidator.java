package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.CategoryExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryExistsValidator implements ConstraintValidator<CategoryExists, Category> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        Long id = category.getId();
        if (id == null) {
            return false;
        }
        return categoryService.existsById(id);
    }
}
