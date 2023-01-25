package de.daniel.birthdaybackend.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BirthdayRequest {
    private String firstName;

    private String lastName;

    private String address;

    private String name;

    private boolean sendEmail;
    private String date;

    private Long contact;
}
