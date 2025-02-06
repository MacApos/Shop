package com.shop.service;

import com.shop.event.EmailEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final MessageService messageService;
    @Qualifier("emailTemplateEngine")
    private final TemplateEngine templateEngine;

    private void sendHtmlMessage(String to, String subject, String htmlBody) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("shop@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMessage(String to, String subject, String template, Locale locale,
                                Map<String, Object> variables) {
        Context context = new Context(locale);
        context.setVariables(variables);
        String htmlBody = templateEngine.process(template, context);
        sendHtmlMessage(to, subject, htmlBody);
    }

    public void sendHtmlMessage(EmailEvent event) {
        Locale locale = event.getLocale();
        String subject = messageService.getMessage(event.getSubjectCode(), locale);
        Context context = new Context(locale);
        context.setVariables(event.getVariables());
        String htmlBody = templateEngine.process(event.getTemplate(), context);
        sendHtmlMessage(event.getTo(), subject, htmlBody);
    }

}
