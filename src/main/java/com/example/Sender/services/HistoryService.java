package com.example.Sender.services;

import com.example.Sender.dto.HistoryDTO;
import com.example.Sender.models.Newsletter;
import com.example.Sender.repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    @Autowired
    private NewsletterRepository newsletterRepository;
    public List<HistoryDTO> getHistory() {
        List<Newsletter> newsletters = (List<Newsletter>) newsletterRepository.findAll();
        return newsletters.stream()
                .map(newsletter -> {
                    HistoryDTO dto = new HistoryDTO();
                    dto.setId(newsletter.getId());
                    dto.setDateTime(newsletter.getDateTime());
                    dto.setDestination(newsletter.getDestination());
                    dto.setText(newsletter.getTopic());
                    dto.setNumberOfSent(newsletter.getNumberOfSent());
                    dto.setStatus(newsletter.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());

    }
}
