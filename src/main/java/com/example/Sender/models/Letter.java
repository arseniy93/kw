package com.example.Sender.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String destinationAddress;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String sentStatus;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String letterType;

    @ManyToOne
    @JoinColumn(name = "newsletter_id", nullable = false)
    private Newsletter newsletter;
}
