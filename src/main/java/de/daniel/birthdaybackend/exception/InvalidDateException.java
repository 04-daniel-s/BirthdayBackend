package de.daniel.birthdaybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE,reason = "Das Datum konnte nicht gespeichert werden")
public class InvalidDateException extends RuntimeException {
}
