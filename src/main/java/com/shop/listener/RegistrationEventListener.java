package com.shop.listener;

import com.shop.model.RegistrationToken;
import com.shop.model.User;
import com.shop.event.RegistrationEvent;
import com.shop.service.EmailService;
import com.shop.service.MessageService;
import com.shop.service.RegistrationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {
    private final RegistrationTokenService registrationTokenService;
    private final EmailService emailService;
    private final MessageService messageService;

    //    @Value("${react.origin}")
    @Value(value = "http://localhost:8080")
    private String origin;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        User user = event.getUser();
        RegistrationToken registrationToken = new RegistrationToken(user);
        registrationTokenService.save(registrationToken);
        Locale locale = event.getLocale();
        String token = registrationToken.getToken();
        sendHtmlMessage(user, locale, token);
    }

    private void sendHtmlMessage(User user, Locale locale, String token) {
        String to = user.getEmail();
        String subject = messageService.getMessage("confirm.registration.subject", locale);
        String url = String.format("%s/confirm-registration?token=%s", origin, token);
        Map<String, Object> variables = Map.of(
                "user", user,
                "url", url);
        String template = "confirm-registration.html";
        emailService.sendHtmlMessage(to, subject, template, variables);
    }
}
