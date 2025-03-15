package com.example.Sender.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MailParams {
    private String topic;
    private String message;
    private String emailTo;
}
