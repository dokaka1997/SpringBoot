package com.viettel.arpu.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Deprecated
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String toLocale(String messageCode, String... params) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, params, locale);
    }
}

