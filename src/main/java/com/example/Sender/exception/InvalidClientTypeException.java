package com.example.Sender.exception;


public class InvalidClientTypeException extends IllegalArgumentException {

    public InvalidClientTypeException(String message) {
        super(message);
    }
}
