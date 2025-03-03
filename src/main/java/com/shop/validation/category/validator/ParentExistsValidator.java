package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.ParentExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentExistsValidator implements ConstraintValidator<ParentExists, Category> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        Category parent = category.getParent();
        if (parent == null) {
            return true;
        }
        Long parentId = parent.getId();
        return parentId != null && categoryService.existsById(parentId);
    }
}
