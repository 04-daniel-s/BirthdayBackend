package de.daniel.birthdaybackend.repository;

import de.daniel.birthdaybackend.controller.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    Optional<Contact> getContactById(Long id);

    void deleteById(Long id);
    boolean existsByFirstNameAndLastName(String firstName,String lastName);

    boolean existsByEmail(String email);
}
