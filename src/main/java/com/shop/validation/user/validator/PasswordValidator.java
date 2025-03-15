package com.shop.validation.user.validator;

import com.shop.validation.user.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password==null){
            return false;
        }
        String language = LocaleContextHolder.getLocale().getLanguage();
        String path = "messages" + (language.equals("en") || language.isEmpty() ? "" : "_" + language);
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/" + path + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessageResolver resolver = new PropertiesMessageResolver(props);
        org.passay.PasswordValidator validator = new org.passay.PasswordValidator(resolver,
                new LengthRule(8, 16),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        for (String message : validator.getMessages(result)) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }
        return false;
    }
}
