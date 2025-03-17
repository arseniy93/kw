package com.example.Sender.models;

import com.example.Sender.annotations.CustomEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String firstname;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String lastname;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "client_type_id", nullable = false)
    private ClientType clientType;

    @CustomEmail
    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;



}
