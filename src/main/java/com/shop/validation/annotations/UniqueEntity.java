package com.shop.validation.annotations;

import com.shop.validation.validator.UniqueEntityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UniqueEntityValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEntity {
    Class<?> service();

    String[] fields();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
