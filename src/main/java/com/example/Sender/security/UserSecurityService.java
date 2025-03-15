package com.example.Sender.security;

import com.example.Sender.models.User;
import com.example.Sender.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final  UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByName(username);
        if (null == user) {
            log.warn("Пользователь {} не найден", username);
            throw new UsernameNotFoundException("Пользователь " + username + " не найден");
        }
        return user;
    }

}
