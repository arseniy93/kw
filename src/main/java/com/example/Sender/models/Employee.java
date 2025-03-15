package com.example.Sender.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {
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
    @JoinColumn(name = "employee_type_id", nullable = false)
    private EmployeeType employeeType;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;

}
