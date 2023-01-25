package de.daniel.birthdaybackend.util;

import de.daniel.birthdaybackend.controller.entity.Birthday;
import de.daniel.birthdaybackend.repository.ContactRepository;
import de.daniel.birthdaybackend.service.BirthdayService;
import de.daniel.birthdaybackend.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@AllArgsConstructor
public class MailScheduler {

    BirthdayService birthdayService;

    ContactRepository contactRepository;
    MailService mailService;

    @Async
    @Scheduled(cron = "* * 8 * * *")
    public void scheduler() {
        for (Birthday birthday : birthdayService.getTomorrowsBirthdays()) {
            if(birthday.isSendEmail()) {
                mailService.sendSimpleMail(birthday.getContact().getEmail(), birthday);
                continue;
            }

            contactRepository.findAll().forEach((email) -> mailService.sendSimpleMail(email.getEmail(), birthday));
        }
    }
}
