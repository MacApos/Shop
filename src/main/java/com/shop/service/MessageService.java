package com.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;

    public Locale getLocale(){
        return LocaleContextHolder.getLocale();
    }

    public String getMessage(String code){
        Locale locale = getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code, Locale locale){
        return messageSource.getMessage(code, null, locale);
    }
}
