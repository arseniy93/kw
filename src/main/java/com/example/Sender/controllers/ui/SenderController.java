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
@RequestMapping("/sender")
public class SenderController {

    private final MainNotificationService mainNotificationService;

    public SenderController(MainNotificationService mainNotificationService) {
        this.mainNotificationService = mainNotificationService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/sender/main";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "sender";
    }

    @PostMapping(
            value = "/send-letter",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveForm(@RequestBody NotificationDTO notificationDTO) {
        mainNotificationService.send(notificationDTO, false);
        return "history";
    }
}
