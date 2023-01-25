package de.daniel.birthdaybackend.repository;

import de.daniel.birthdaybackend.controller.entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log,Long> {
}
