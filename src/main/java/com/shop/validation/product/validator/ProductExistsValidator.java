package com.shop.validation.product.validator;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.validation.product.annotation.ProductExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductExistsValidator implements ConstraintValidator<ProductExists, Product> {
    private final ProductService productService;

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        Long id = product.getId();
        if (id == null) {
            return false;
        }
        return productService.existsById(product.getId());
    }
}
