package com.shop.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

@Component
public abstract class AbstractService<T> {

    private LocalValidatorFactoryBean validatorFactory;

    private JpaRepository<T, Long> repository;

    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void validate(T entity, Class<?>... groups) {
        if (groups.length == 0) {
            groups = new Class[]{Default.class};
        }

        Set<ConstraintViolation<T>> constraintViolations = validatorFactory.validate(entity, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @Autowired
    public void setRepository(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Autowired
    public void setValidatorFactory(LocalValidatorFactoryBean validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

}
