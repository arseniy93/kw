package com.example.Sender.models;

import com.example.Sender.annotations.CustomEmail;
import com.example.Sender.enums.EntityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String firstname;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String lastname;

    @Column(columnDefinition = "TEXT")
    private String middleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    private EntityType employeeEntityType;

    @CustomEmail
    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;

}
