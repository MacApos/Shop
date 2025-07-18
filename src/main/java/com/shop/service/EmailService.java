package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.event.EmailEvent;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final MessageService messageService;
    private final RegistrationTokenService registrationTokenService;

    @Value("${react.origin}")
    private String origin;

    @Qualifier("emailTemplateEngine")
    private final TemplateEngine templateEngine;

    private void sendHtmlMessage(String to, String subject, String htmlBody) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMessage(String to, String subject, String template,
                                Map<String, Object> variables) {
        Locale locale = LocaleContextHolder.getLocale();
        Context context = new Context(locale);
        context.setVariables(variables);
        String htmlBody = templateEngine.process(template, context);
        sendHtmlMessage(to, subject, htmlBody);
    }

    @Async
    public void sendTokenEmail(String to, String subjectCode, String template, Map<String, Object> variables) {
        sendHtmlMessage(to, messageService.getMessage(subjectCode), template, variables);
    }

    @Async
    public void sendTokenEmail(User user, String subjectCode, String template, String url) {
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(user);
        sendHtmlMessage(user.getEmail(),
                messageService.getMessage(subjectCode), template,
                Map.of("user", user, "url", origin + url + registrationToken.getToken()));
    }
}
