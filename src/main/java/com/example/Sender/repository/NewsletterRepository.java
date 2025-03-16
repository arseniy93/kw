package com.example.Sender.repository;

import com.example.Sender.models.Newsletter;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface NewsletterRepository extends CrudRepository<Newsletter, Long> {
    Newsletter getByDateTime(LocalDateTime dateTime);
}
