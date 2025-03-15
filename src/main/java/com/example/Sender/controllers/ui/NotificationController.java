package com.example.Sender.controllers.ui;

import com.example.Sender.dto.NotificationDTO;
import com.example.Sender.services.MainNotificationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notification")
public class NotificationController {
    private final MainNotificationService mainNotificationService;

    public NotificationController(MainNotificationService mainNotificationService) {
        this.mainNotificationService = mainNotificationService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/notification/main";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "notification";
    }

    @PostMapping(value = "/send-notification",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sendMessage(@RequestBody NotificationDTO notificationDTO) {
        mainNotificationService.send(notificationDTO, true);
        return "history";
    }
}