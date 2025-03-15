package com.example.Sender.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/*",
            "/css/*",
            "/js/*",
            "/images/*",
            "/",
            "/user/*",
            "/error/*/*",
//           ,
            "/signup",
            "/index"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/admin/**",
                                        "/console/*",
                                        "/custom-swagger-ui",  // Если у вас есть кастомизированная страница
                                        "/swagger-resources/**",   // Доступ к swagger-resources
                                        "/swagger-ui.html",      // Доступ к основному файлу swagger-ui.html
                                        "/v3/api-docs.yaml",      // Доступ к файлу спецификации OpenAPI (YAML)
                                        "/v3/api-docs.json")    // Доступ к файлу спецификации OpenAPI (JSON)
                                .hasRole("ADMIN") // или hasAnyRole("ADMIN") - если это строка "ADMIN" без префикса от базы
                                .requestMatchers("/sender/**")
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                                .anyRequest().authenticated()// Остальные требуют авторизации
                )
                .formLogin(form -> form
                        .loginPage("/index") // Путь к странице входа
                        .defaultSuccessUrl("/sender/main", true) // Успешный вход
                        .failureUrl("/index?error") // Ошибка входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .invalidateHttpSession(true) // Завершение сессии
                        .clearAuthentication(true)  // Очистка авторизации
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
        // Отключение CSRF для разработки
    }

}


//package com.example.Sender.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//public class SecurityConfig {
//
//    private static final String[] PUBLIC_MATCHERS = {
//            "/webjars/*",
//            "/css/*",
//            "/js/*",
//            "/images/*",
//            "/",
//            "/user/*",
//            "/error/*/*",
//            "/console/*",
//            "/signup"
//    };
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests()
//                .requestMatchers("/sender/**", "/notification/**", "/db/**", "/history/**")
//                .hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers(PUBLIC_MATCHERS)
//                .permitAll().anyRequest().authenticated();
//
//        http
//                .csrf().disable().cors().disable()
//                .formLogin()
//                .failureUrl("/index?error")
//                .loginPage("/index")
//                .defaultSuccessUrl("/sender/main", true)
//                .permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/index?logout")
//                .permitAll();
//        return http.build();
//    }
//
//}