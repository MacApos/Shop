package com.shop.validator.impl;

import org.springframework.validation.Validator;

public abstract class ValidatorService<T> implements Validator {
    protected T obj;

    public ValidatorService(T obj) {
        this.obj = obj;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return obj.getClass().equals(clazz);
    }

}
