package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.ParentHasChildren;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentHasChildrenValidator implements ConstraintValidator<ParentHasChildren, Category> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category parent, ConstraintValidatorContext constraintValidatorContext) {
        Category parentById = categoryService.findById(parent.getId());
        boolean empty = parent.getChildren().isEmpty();
        return false;
    }
}
