package com.example.Sender.repository;

import com.example.Sender.models.Newsletter;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

//TODO обычно пишут Long вместо Integer, я не стал. думаю база не будет большая. но так на будущее
public interface NewsLetterRepository extends CrudRepository<Newsletter, Integer> {
    Newsletter getByDateTime(LocalDateTime dateTime);
}
