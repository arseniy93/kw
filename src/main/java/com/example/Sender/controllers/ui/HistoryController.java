package com.example.Sender.controllers.ui;

import com.example.Sender.dto.HistoryDTO;
import com.example.Sender.models.Newsletter;
import com.example.Sender.repository.NewsletterRepository;
import com.example.Sender.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private NewsletterRepository newsletterRepository;

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

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
        Newsletter newsletter = newsletterRepository.getByDateTime(dateTime);
        model.addAttribute("newsletter", newsletter);
        return "letter";
    }
}
