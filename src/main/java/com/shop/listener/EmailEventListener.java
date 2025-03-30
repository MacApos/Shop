package com.shop.listener;

import com.shop.event.EmailEvent;
import com.shop.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// delete
@Component
@RequiredArgsConstructor
public class EmailEventListener implements ApplicationListener<EmailEvent> {
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(EmailEvent event) {
        emailService.sendHtmlMessage(event);
    }
}
