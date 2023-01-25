package de.daniel.birthdaybackend.service;

import de.daniel.birthdaybackend.controller.entity.Birthday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMail(String to, Birthday birthday) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Erinnerung");
        mailMessage.setText("Hey, morgen hat " + birthday.getFirstName() + " " + birthday.getLastName() + " Geburtstag und wird " + getAge(birthday) + " Jahre alt!");
        mailMessage.setFrom(sender);
        mailMessage.setTo(to);

        javaMailSender.send(mailMessage);
    }

    private int getAge(Birthday birthday) {
        LocalDate bd = birthday.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(bd, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return period.getYears();
    }
}
