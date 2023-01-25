package de.daniel.birthdaybackend.util;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSender {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.prointernet.com");
        mailSender.setPort(25);
        mailSender.setProtocol("smtp");
        return mailSender;
    }
}
