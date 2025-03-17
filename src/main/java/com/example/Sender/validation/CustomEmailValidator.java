package com.example.Sender.validation;

import com.example.Sender.annotations.CustomEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CustomEmailValidator implements ConstraintValidator<CustomEmail, String> {

    private String regexDefault = "";

    @Override
    public void initialize(CustomEmail constraintAnnotation) {
        regexDefault = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        return email.matches(regexDefault);
    }
}


