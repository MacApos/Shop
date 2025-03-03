package com.shop.validation.product.annotation;

import com.shop.validation.product.validator.UniqueProductValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueProductValidator.class})
public @interface UniqueProduct {

    String message() default "{already.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
