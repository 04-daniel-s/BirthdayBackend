package de.daniel.birthdaybackend.repository;

import de.daniel.birthdaybackend.controller.entity.Birthday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdayRepository extends CrudRepository<Birthday,Long> {


    void deleteById(Long id);
    Birthday getBirthdayById(Long id);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
