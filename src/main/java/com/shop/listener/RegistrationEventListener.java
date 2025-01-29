package com.shop.listener;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
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

    @Value("${react.origin}")
    private String origin;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        User user = event.getUser();
        RegistrationToken registrationToken = new RegistrationToken(user);
        registrationTokenService.save(registrationToken);
        Locale locale = event.getLocale();
        sendHtmlMessage(user, locale, registrationToken);
    }

    private void sendHtmlMessage(User user, Locale locale, RegistrationToken registrationToken) {
        String to = user.getEmail();
        String subject = messageService.getMessage("registration.confirm.subject", locale);
        String token = registrationToken.getToken();
        String url = String.format("%sconfirm-registration?token=%s", origin, token);
        Map<String, Object> variables = Map.of(
                "user", user,
                "url", url);
        String template = "registration-confirm.html";
        emailService.sendMessageUsingThymeleafTemplate(to, subject, variables, locale, template);
    }
}
