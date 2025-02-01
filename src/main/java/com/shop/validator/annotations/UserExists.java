package com.shop.validator.annotations;

import com.shop.validator.UserExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UserExistsValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExists {

    String message() default "{does.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
