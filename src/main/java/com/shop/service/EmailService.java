package com.shop.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendSimpleMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo("u1326546@gmail.com");
        message.setSubject("subject");
        message.setText("test");
        emailSender.send(message);

        MimeMessage mimeMessage = emailSender.createMimeMessage();

    }
}
