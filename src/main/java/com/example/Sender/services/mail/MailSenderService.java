package com.example.Sender.services.mail;

import com.example.Sender.dto.MailParams;

public interface MailSenderService {
    String send(MailParams mailParams);
}
