package com.shop.validation.annotation;

import com.shop.validation.validator.UsernameTakenValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UsernameTakenValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameTaken {

    String message() default "{user.username.already.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
