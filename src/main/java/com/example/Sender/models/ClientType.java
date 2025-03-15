package com.example.Sender.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ClientType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "clientType")
    private List<Client> clients;
}
