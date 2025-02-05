package com.shop.validation.annotation;

import com.shop.validation.validator.UserAlreadyEnabledValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UserAlreadyEnabledValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAlreadyEnabled {

    String message() default "{user.already.enabled}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
