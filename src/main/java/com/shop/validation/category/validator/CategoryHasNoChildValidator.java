package com.shop.validation.category.validator;

import com.shop.model.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.CategoryHasNoChild;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryHasNoChildValidator implements ConstraintValidator<CategoryHasNoChild, Category> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        Long id = category.getId();
        return id != null && !categoryService.existsByParent(id);
    }
}
