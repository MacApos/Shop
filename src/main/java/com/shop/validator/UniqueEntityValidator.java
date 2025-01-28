package com.shop.validator;

import com.shop.service.MessageService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@RequiredArgsConstructor
public class UniqueEntityValidator implements ConstraintValidator<UniqueEntity, Object> {
    private final ApplicationContext context;
    private final MessageSource messageSource;
    private final MessageService messageSourceService;

    private Class<?> service;
    private List<String> fields = new ArrayList<>();

    @Override
    public void initialize(UniqueEntity constraintAnnotation) {
        service = constraintAnnotation.service();
        fields = Arrays.asList(constraintAnnotation.fields());
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext validatorContext) {
        Object bean = context.getBean(service);
        String simpleName = obj.getClass().getSimpleName();
        boolean isValid = true;
        validatorContext.disableDefaultConstraintViolation();

        for (String field : fields) {
            if (entityExists(obj, field, bean)) {
                addConstraintViolation(validatorContext, simpleName, field);
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean entityExists(Object obj, String field, Object bean) {
        try {
            String value = getDeclaredFieldValue(obj, field);
            Method declaredMethod = getDeclaredMethod(bean, field);
            Object entity = declaredMethod.invoke(bean, value);
            return entity != null;
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDeclaredFieldValue(Object obj, String field) throws IllegalAccessException, NoSuchFieldException {
        Field declaredField = obj.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        return (String) declaredField.get(obj);
    }

    private Method getDeclaredMethod(Object bean, String field) throws NoSuchMethodException {
        String methodName = "findBy" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
        Method declaredMethod = bean.getClass().getDeclaredMethod(methodName, String.class);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    private void addConstraintViolation(ConstraintValidatorContext validatorContext, String entityName, String field) {
        validatorContext
                .buildConstraintViolationWithTemplate(messageSourceService.getMessage(
                        String.join(".", entityName.toLowerCase(), field, "already.exists")
                ))
                .addPropertyNode(field)
                .addConstraintViolation();
    }

}