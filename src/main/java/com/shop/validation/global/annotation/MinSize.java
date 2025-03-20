package com.shop.validation.global.annotation;

import com.shop.validation.global.validator.MinSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MinSize.List.class)
@Constraint(validatedBy = {MinSizeValidator.class})
public @interface MinSize {
    String message() default "{jakarta.validation.constraints.Size.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 3;

    int max() default Integer.MAX_VALUE;

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        MinSize[] value();
    }
}
