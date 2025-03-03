package com.shop.validation.product.annotation;

import com.shop.validation.product.validator.ProductExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ProductExistsValidator.class})
public @interface ProductExists {

    String message() default "{does.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
