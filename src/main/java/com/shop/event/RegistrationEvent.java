package com.shop.event;

import com.shop.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {
    private User user;
    private Locale locale;

    public RegistrationEvent(User user, Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }
}
