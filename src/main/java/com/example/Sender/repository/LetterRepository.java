package com.example.Sender.repository;


import com.example.Sender.models.Letter;
import org.springframework.data.repository.CrudRepository;

public interface LetterRepository extends CrudRepository<Letter, Integer> {
}
