package com.shop.validation.category.validator;

import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.validation.category.annotation.CategoryExistsById;
import com.shop.validation.product.annotation.ProductExistsById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryExistsByIdValidator implements ConstraintValidator<CategoryExistsById, Long> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return categoryService.existsById(id);
    }
}
