package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.ParentExistsById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentExistsByIdValidator implements ConstraintValidator<ParentExistsById, Category> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        Category parent = category.getParent();
        if (parent == null) {
            return true;
        }
        Long parentId = parent.getId();
        if (parentId == null) {
            return false;
        }

        Long id = category.getId();
        if (id != null && id.equals(parentId)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{category.and.parent.equal}")
                    .addConstraintViolation();
            return false;
        }

        return categoryService.existsById(parentId);
    }
}
