package com.shop.validation.category.validator;

import com.shop.model.Category;
import com.shop.service.CategoryService;
import com.shop.service.ValidatorService;
import com.shop.validation.category.annotation.ParentIsNotItselfChild;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentIsNotItselfChildValidator implements ConstraintValidator<ParentIsNotItselfChild, Category> {
    private final CategoryService categoryService;
    private final ValidatorService validatorService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        Category parent = category.getParent();
        if (parent == null) {
            return true;
        }

        parent = categoryService.findById(parent.getId());
        Category existingCategory = categoryService.findById(category.getId());
        while (parent != null) {
            if (parent.equals(existingCategory)) {
                validatorService.addConstraint(constraintValidatorContext, "{parent.is.itself.child}",
                        "parent");
                return false;
            }
            parent = parent.getParent();
        }
        return true;
    }
}
