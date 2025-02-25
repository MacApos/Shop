package com.shop.validation.user.annotation;

import com.shop.validation.user.validator.UserAlreadyExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UserAlreadyExistValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAlreadyExist {

    String message() default "{user.username.already.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
