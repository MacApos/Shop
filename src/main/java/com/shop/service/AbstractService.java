package com.shop.service;

import com.shop.mapper.GenericMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

@Component
@RequiredArgsConstructor
public abstract class AbstractService<T> {

    private LocalValidatorFactoryBean validatorFactory;

    private GenericMapper<T> mapper;

    private JpaRepository<T, Long> repository;

    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(T source, T target) {
        mapper.update(source, target);
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

//    @Autowired
//    public void setMapper(GenericMapper<T> mapper) {
//        this.mapper = mapper;
//    }

}
