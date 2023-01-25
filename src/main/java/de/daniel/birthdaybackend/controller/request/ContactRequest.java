package de.daniel.birthdaybackend.controller.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactRequest {
    private String firstName;

    private String lastName;

    private String email;
}
