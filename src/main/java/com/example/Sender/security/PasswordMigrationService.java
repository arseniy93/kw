package com.example.Sender.security;

import com.example.Sender.models.User;
import com.example.Sender.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PasswordMigrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void migratePasswords() {
        List<User> users = userService.findAllUsers();
        for (User user : users) {
            if (!user.getPassword().startsWith("$2a$")) { // Проверка, зашифрован ли пароль (BCrypt начинается с "$2a$")
                log.info("User's password was encrypted");
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.save(user);
            }
        }
    }
}