package com.example.Sender.controllers.ui;

import com.example.Sender.dto.HistoryDTO;
import com.example.Sender.models.Newsletter;
import com.example.Sender.services.HistoryService;
import com.example.Sender.services.NewsLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final NewsLetterService letterService;

    private final HistoryService historyService;


    @GetMapping("/")
    public String home() {
        return "redirect:/history/main";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<HistoryDTO> newsletterDTOs = historyService.getHistory();
        model.addAttribute("newsletters", newsletterDTOs);
        return "history";
    }

    @GetMapping("/letter/{dateTime}")
    public String getNewsletterByDateTime(@PathVariable LocalDateTime dateTime, Model model) {
        Newsletter newsletter = letterService.getByDateTime(dateTime);
        model.addAttribute("newsletter", newsletter);
        return "letter";
    }
}
