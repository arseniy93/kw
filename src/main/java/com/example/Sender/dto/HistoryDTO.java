package com.example.Sender.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String destination;
    private String text;
    private Integer numberOfSent;
    private String status;
}
