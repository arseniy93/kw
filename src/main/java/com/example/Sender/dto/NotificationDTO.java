package com.example.Sender.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String topicText;
    private String messageText;
    private String messageType;
    private String entityType;

}
