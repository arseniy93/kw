package com.example.Sender.controllers.rest;


import com.example.Sender.dto.HistoryDTO;
import com.example.Sender.models.Newsletter;
import com.example.Sender.services.HistoryService;
import com.example.Sender.services.NewsLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = HistoryRestController.REST_URL)
public class HistoryRestController {
    static final String REST_URL = "/history/rest";

    private final NewsLetterService letterService;

    private final HistoryService historyService;

    @GetMapping(value = "/main", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HistoryDTO>> mainPage() {
        List<HistoryDTO> newsletterDTOs = historyService.getHistory();

        if (newsletterDTOs != null && !newsletterDTOs.isEmpty()) {
            return ResponseEntity.ok(newsletterDTOs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/{dateTime}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Newsletter> getNewsletterByDateTime(@PathVariable LocalDateTime dateTime) {
        Newsletter newsletter = letterService.getByDateTime(dateTime);

        if (newsletter != null) {
            return ResponseEntity.ok(newsletter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
