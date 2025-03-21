package com.example.Sender.repository;

import com.example.Sender.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
