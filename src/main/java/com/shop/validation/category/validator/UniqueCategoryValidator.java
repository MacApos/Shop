package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.service.ValidatorService;
import com.shop.validation.category.annotation.UniqueCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, Category> {
    private final ValidatorService validatorService;
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        String name = category.getName();
        Category parent = category.getParent();
        if (parent == null) {
            return !categoryService.existsByNameAndParentIsNull(name);
        }

        Long id = category.getId();
        Long parentId = parent.getId();
        if (id != null && id.equals(parentId)) {
            validatorService.addConstraint(constraintValidatorContext, "{category.and.parent.equal}");
            return false;
        }

        parent = categoryService.findById(parentId);
        if (name.equals(parent.getName())) {
            validatorService.addConstraint(constraintValidatorContext, "{category.and.parent.name.equal}");
            return false;
        }

        return !categoryService.existsByNameAndParent(name, parent);
    }
}