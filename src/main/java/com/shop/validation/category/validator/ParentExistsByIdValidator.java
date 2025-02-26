package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.annotation.CategoryExistsById;
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
    public boolean isValid(Category parent, ConstraintValidatorContext constraintValidatorContext) {
        if(parent==null){
            return true;
        }
        Long id = parent.getId();
        if(id==null){
            return false;
        }
        return categoryService.existsById(id);
    }
}
