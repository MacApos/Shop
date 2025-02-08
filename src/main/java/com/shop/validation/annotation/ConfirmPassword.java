package com.shop.validation.annotation;

import com.shop.validation.validator.ConfirmPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ConfirmPasswordValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {

    String message() default "{invalid.password.confirmation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
