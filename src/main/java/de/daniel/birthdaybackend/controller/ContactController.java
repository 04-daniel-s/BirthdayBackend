package de.daniel.birthdaybackend.controller;

import de.daniel.birthdaybackend.controller.entity.Birthday;
import de.daniel.birthdaybackend.controller.entity.Contact;
import de.daniel.birthdaybackend.controller.request.BirthdayRequest;
import de.daniel.birthdaybackend.controller.request.ContactRequest;
import de.daniel.birthdaybackend.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Data
public class ContactController {

    ContactRepository repository;

    @GetMapping("/contact/all")
    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        repository.findAll().forEach(v -> contacts.add(v));
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/contact/post")
    public ResponseEntity<Contact> postContact(@RequestBody @Validated ContactRequest request) {
        if (repository.existsByEmail(request.getEmail()) || repository.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName()))
            throw new RuntimeException();
        Contact contact = Contact.builder().lastName(request.getLastName()).firstName(request.getFirstName()).email(request.getEmail()).build();
        repository.save(contact);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/contact/delete/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable Long id) {
        Optional<Contact> contact = repository.getContactById(id);
        if(!contact.isPresent()) throw new RuntimeException();

        try{
        repository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Dieser Ansprechpartner kann nicht gelöscht werden, da er zugewiesene Kontakte hat");
        }
        return ResponseEntity.ok(contact.get());
    }

    @PutMapping("/contact/update/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Validated @RequestBody ContactRequest request) {
        Optional<Contact> contact = repository.getContactById(id);
        if(!contact.isPresent()) throw new RuntimeException();
        contact.get().setEmail(request.getEmail());
        contact.get().setFirstName(request.getFirstName());
        contact.get().setLastName(request.getLastName());
        repository.save(contact.get());
        return ResponseEntity.ok(contact.get());
    }
}