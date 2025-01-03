package com.ict.edu.common.util;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    private final JavaMailSender javaMailSender;
    private final MimeMessage message;
    private final MimeMessageHelper messageHelper;

    public EmailService(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        this.message = this.javaMailSender.createMimeMessage();
        this.messageHelper = new MimeMessageHelper(message, true, "utf-8");
    }
    
    // 제목을 설정한다.
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }
    
    // 본문을 설정한다.
    public void setText(String text) throws MessagingException {
        messageHelper.setText(text, true);
    }
    
    // 보내는 사람을 설정한다.
    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
        messageHelper.setFrom(email, name);
    }
    
    // 받는 사람을 설정한다.
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }
    
    // 보낸다.
    public void send() {
        javaMailSender.send(message);
    }
    
}