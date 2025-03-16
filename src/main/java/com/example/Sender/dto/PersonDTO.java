package com.example.Sender.dto;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String middleName;
    private String type;
    private String email;

    public PersonDTO(Long id, String firstname, String lastname, String middleName, String type, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middleName = middleName;
        this.type = type;
        this.email = email;
    }
}
