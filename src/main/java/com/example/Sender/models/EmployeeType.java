package com.example.Sender.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class EmployeeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "employeeType")
    private List<Employee> employees;
}
