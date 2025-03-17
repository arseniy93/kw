package com.example.Sender.controllers.rest;

import com.example.Sender.dto.NotificationDTO;
import com.example.Sender.services.MainNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = NotificationRestController.REST_URL)
public class NotificationRestController {
    static final String REST_URL = "/notification/rest";
    private final MainNotificationService mainNotificationService;

    @PostMapping(value = "/send-notification",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody NotificationDTO notificationDTO) {
        mainNotificationService.send(notificationDTO, true);
        return ResponseEntity.ok("Уведомление отправлено успешно");
    }
}
