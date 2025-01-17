package com.shop.service;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleService {
    public Locale getLocale(){
        return LocaleContextHolder.getLocale();
    }
}
