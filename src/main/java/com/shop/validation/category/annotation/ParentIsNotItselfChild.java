package com.shop.validation.category.annotation;

import com.shop.validation.category.validator.ParentHasNoProductsValidator;
import com.shop.validation.category.validator.ParentIsNotItselfChildValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ParentIsNotItselfChildValidator.class})
public @interface ParentIsNotItselfChild {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
