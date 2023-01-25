package de.daniel.birthdaybackend.controller;

import de.daniel.birthdaybackend.controller.entity.Birthday;
import de.daniel.birthdaybackend.controller.entity.Contact;
import de.daniel.birthdaybackend.controller.entity.Log;
import de.daniel.birthdaybackend.controller.request.BirthdayRequest;
import de.daniel.birthdaybackend.exception.InvalidDateException;
import de.daniel.birthdaybackend.exception.NameAlreadyTakenException;
import de.daniel.birthdaybackend.repository.BirthdayRepository;
import de.daniel.birthdaybackend.repository.ContactRepository;
import de.daniel.birthdaybackend.repository.LogRepository;
import de.daniel.birthdaybackend.service.BirthdayService;
import de.daniel.birthdaybackend.service.MailService;
import de.daniel.birthdaybackend.util.ActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Data
@AllArgsConstructor
public class BirthdayController {

    BirthdayRepository repository;

    LogRepository logRepository;

    BirthdayService service;

    MailService emailService;

    ContactRepository contactRepository;

    @GetMapping("/birthdays/all")
    public ResponseEntity<List<Birthday>> getBirthdays() {
        return ResponseEntity.ok(service.getSortedBirthdays());
    }

    @PostMapping("/birthdays/post")
    public ResponseEntity<Birthday> postBirthday(@RequestBody @Validated BirthdayRequest request) {
        if (getRepository().existsByFirstNameAndLastName(request.getFirstName().trim(), request.getLastName().trim()))
            throw new NameAlreadyTakenException();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(request.getDate());
        } catch (ParseException e) {
            throw new InvalidDateException();
        }

        Optional<Contact> contact = contactRepository.getContactById(request.getContact());
        if(!contact.isPresent()) throw new RuntimeException();

        Birthday birthday = Birthday.builder().firstName(request.getFirstName()).lastName(request.getLastName()).date(date).address(request.getAddress()).contact(contact.get()).sendEmail(request.isSendEmail()).build();

        logRepository.save(Log.builder().date(new Date()).action(ActionEnum.CREATE).name(request.getFirstName() + " " + request.getLastName()).build());
        repository.save(birthday);
        return ResponseEntity.ok(birthday);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Birthday> deleteBirthday(@PathVariable Long id) {
        Birthday birthday = repository.getBirthdayById(id);
        logRepository.save(Log.builder().date(new Date()).action(ActionEnum.DELETE).name(birthday.getFirstName() + " " + birthday.getLastName()).build());
        repository.deleteById(id);
        return ResponseEntity.ok(birthday);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Birthday> updateBirthday(@PathVariable Long id, @Validated @RequestBody BirthdayRequest request) {
        Birthday birthday = repository.getBirthdayById(id);
        Optional<Contact> contact = contactRepository.getContactById(request.getContact());
        if(!contact.isPresent()) throw new RuntimeException("Es ist ein Fehler aufgetreten!");

        if (getRepository().existsByFirstNameAndLastName(request.getFirstName().trim(), request.getLastName().trim()) && !birthday.getFirstName().equalsIgnoreCase(request.getFirstName()))
            throw new NameAlreadyTakenException();

        Date date;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(request.getDate());
        } catch (ParseException e) {
            throw new InvalidDateException();
        }

        logRepository.save(Log.builder().date(new Date()).action(ActionEnum.EDIT).name(birthday.getFirstName() + " " + birthday.getLastName()).build());

        birthday.setFirstName(request.getFirstName());
        birthday.setLastName(request.getLastName());
        birthday.setDate(date);
        birthday.setAddress(request.getAddress());
        birthday.setSendEmail(request.isSendEmail());
        birthday.setContact(contact.get());
        repository.save(birthday);

        return ResponseEntity.ok(birthday);
    }

    @PutMapping("/updateSendMail/{id}")
    public ResponseEntity<Birthday> updateSendMail(@PathVariable Long id) {
        Birthday birthday = repository.getBirthdayById(id);
        logRepository.save(Log.builder().date(new Date()).action(ActionEnum.EDIT).name(birthday.getFirstName() + " " + birthday.getLastName()).build());
        birthday.setSendEmail(!birthday.isSendEmail());
        repository.save(birthday);
        return ResponseEntity.ok(birthday);
    }
}
