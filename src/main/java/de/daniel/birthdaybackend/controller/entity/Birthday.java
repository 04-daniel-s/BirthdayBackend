package de.daniel.birthdaybackend.controller.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Birthday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contact contact;
    private String firstName;

    private String lastName;

    private String address;

    private boolean sendEmail;

    private Date date;
}
