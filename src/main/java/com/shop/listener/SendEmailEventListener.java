package com.shop.listener;

import com.shop.event.SendEmailEvent;
import com.shop.service.EmailService;
import com.shop.service.RegistrationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SendEmailEventListener implements ApplicationListener<SendEmailEvent> {
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(SendEmailEvent event) {
        emailService.sendHtmlMessage(event);
    }
}
