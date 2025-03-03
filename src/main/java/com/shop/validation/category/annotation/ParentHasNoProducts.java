package com.shop.validation.category.annotation;

import com.shop.validation.category.validator.ParentHasNoProductsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ParentHasNoProductsValidator.class})
public @interface ParentHasNoProducts {

    String message() default "{parent.has.products}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
