package com.example.Sender.services;

import com.example.Sender.models.Newsletter;
import com.example.Sender.repository.NewsLetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NewsLetterService {

    private final NewsLetterRepository newsLetterRepository;

    public void save(Newsletter newsletter){
        newsLetterRepository.save(newsletter);
    }

    public Newsletter getByDateTime(LocalDateTime dateTime){
        return newsLetterRepository.getByDateTime(dateTime);
    }
}
