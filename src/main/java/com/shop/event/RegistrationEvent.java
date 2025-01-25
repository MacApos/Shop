package com.shop.event;

import com.shop.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {
    private String url;
    private Locale locale;
    private User user;

    public RegistrationEvent(String url, Locale locale, User user) {
        super(user);
        this.url = url;
        this.locale = locale;
        this.user = user;
    }
}
