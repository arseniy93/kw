package com.example.Sender.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Newsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String destination;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String topic;

    @Column(nullable = false)
    private Integer numberOfSent;

    @Column(columnDefinition = "TEXT")
    private String status;

    @OneToMany(mappedBy = "newsletter")
    private List<Letter> letters;

}
