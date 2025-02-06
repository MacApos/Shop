package com.shop.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@NotNull
@Email
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Repeatable(NotNullEmail.List.class)
@ReportAsSingleViolation
public @interface NotNullEmail {
    String message() default "{jakarta.validation.constraints.Email.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        NotNullEmail[] value();
    }
}
