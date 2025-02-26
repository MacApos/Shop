package com.shop.validation.category.annotation;

import com.shop.validation.category.validator.ParentExistsByIdValidator;
import com.shop.validation.category.validator.ValidNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ParentExistsByIdValidator.class})
public @interface ParentExistsById {

    String message() default "{does.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
