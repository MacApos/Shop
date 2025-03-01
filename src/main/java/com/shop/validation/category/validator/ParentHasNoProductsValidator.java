package com.shop.validation.category.validator;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.validation.category.annotation.ParentHasNoProducts;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentHasNoProductsValidator implements ConstraintValidator<ParentHasNoProducts, Category> {
    private final ProductService productService;

    @Override
    public boolean isValid(Category parent, ConstraintValidatorContext constraintValidatorContext) {
        if (parent == null) {
            return true;
        }
        return !productService.existByCategoryId(parent.getId());
    }
}
