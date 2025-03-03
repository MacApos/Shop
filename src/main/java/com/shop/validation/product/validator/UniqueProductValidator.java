package com.shop.validation.product.validator;

import com.shop.service.ProductService;
import com.shop.validation.product.annotation.UniqueProduct;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueProductValidator implements ConstraintValidator<UniqueProduct, Long> {
    private final ProductService productService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return productService.existsById(id);
    }
}
