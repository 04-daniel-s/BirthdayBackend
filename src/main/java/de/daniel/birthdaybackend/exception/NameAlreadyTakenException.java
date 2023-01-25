package de.daniel.birthdaybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE,reason = "Der Name ist bereits vergeben")
public class NameAlreadyTakenException extends RuntimeException {

}
