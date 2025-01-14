package com.shop.validator.impl;

import com.shop.entity.User;
import com.shop.interceptor.ServiceInterface;
import com.shop.service.UserService;
import com.shop.validator.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, User> {
    private final UserService userService;
    private final ApplicationContext applicationContext;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        boolean b = userService.findByUsername(user.getUsername()) == null;
        return true;
    }

    //    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
//
//        Class<?> clazz = obj.getClass();
//        Field[] declaredFields = clazz.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            String name = declaredField.getName();
//            boolean b = fields.containsKey(name);
//            if (fields.containsKey(name)) {
//                declaredField.setAccessible(true);
//                try {
//                    String value = (String) declaredField.get(obj);
//                    fields.put(name, value);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//        User admin = userService.findByUsername("admin");

//
//        for (Map.Entry<String, String> entry : fields.entrySet()) {
//            try {
//                Method declaredMethod = clazz.getDeclaredMethod("findBy" + entry.getValue());
//
//            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        }


//        field1.setAccessible(true);
//        Object o;
//        try {
//             o = field1.get(obj);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//        return true;
//    }

}
