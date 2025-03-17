package com.example.Sender.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO turn into headlines to constant
@ControllerAdvice(basePackages = "com.example.Sender.controllers")
@Slf4j
public class ExceptionsHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Нарушение целостности данных: " + ex.getMessage());
        String errorMessage = "Произошло нарушение целостности данных. Проверьте введенные данные.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Ошибка в заполненных полях: " + ex.getMessage());
        String errorMessage = "Ошибка в заполненных полях.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(InvalidClientTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidClientType(InvalidClientTypeException ex) {
        log.error("Неверный тип сущности: " + ex.getMessage());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
