package com.shop.validation.category.annotation;

import com.shop.validation.category.validator.ParentExistsByIdValidator;
import com.shop.validation.category.validator.ParentHasChildrenValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ParentHasChildrenValidator.class})
public @interface ParentHasChildren {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
