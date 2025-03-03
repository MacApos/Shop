package com.shop.validation.category.annotation;

import com.shop.validation.category.validator.CategoryHasNoChildValidator;
import com.shop.validation.product.validator.ProductExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CategoryHasNoChildValidator.class})
public @interface CategoryHasNoChild {

    String message() default "{category.has.children}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
