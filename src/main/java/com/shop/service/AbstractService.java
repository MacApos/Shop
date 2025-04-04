package com.shop.service;

import com.shop.mapper.GenericMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.groups.Default;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

@Component
@Setter
public abstract class AbstractService<T> {

    private LocalValidatorFactoryBean validatorFactory;

    private JpaRepository<T, Long> repository;

    private GenericMapper<T> mapper;

    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void merge(T entity) {
    }

    @Transactional
    public void update(T source, T target) {
        if (mapper == null) {
            throw new RuntimeException("Mapper is not set");
        }
        mapper.update(source, target);
        merge(target);
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
