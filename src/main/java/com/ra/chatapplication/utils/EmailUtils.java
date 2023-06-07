package com.ra.chatapplication.utils;


import com.ra.chatapplication.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Utility class for email operations.
 */
@Component
public class EmailUtils {
    public static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(helper.getMimeMessage());

        } catch (MessagingException e) {
            logger.error("Failed to send email" + e.getMessage());
        }
    }

    public void sendDefaultPasswordHtmlMail(User user, String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);

        String content = templateEngine.process("email/default-password-email", context);
        System.out.println(content);

        sendMail(user.getEmail(), "Welcome to Chatroom - Your Default Password", content);
    }
}

