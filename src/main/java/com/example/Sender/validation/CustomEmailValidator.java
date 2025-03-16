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


//public class ConstraintValidator {
//    private User user;
//    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//// TODO do test this method
//    public boolean isPossibleNumber(String phoneNumber, String codeOfCountryByAlpha2){
//        Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber();
//        number.setCountryCode(Integer.parseInt(getCountryCodeByAlpha2Code(codeOfCountryByAlpha2).get()))
//                .setNationalNumber(Long.parseLong(phoneNumber));
//        return phoneUtil.isPossibleNumber(number);
//    }
//
//    public boolean isValidAndLengthPhoneNumber(String phoneNumberString, String codeOfCountryByAlpha2) {
//
//        try {
//            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parseAndKeepRawInput(phoneNumberString, codeOfCountryByAlpha2);
//            boolean isValid = phoneUtil.isValidNumber(phoneNumber);
//            String numberWithoutFormatting = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
//            int length = numberWithoutFormatting.length();
//            return isValid && length >= 12 && length <= 13;
//        } catch (NumberParseException e) {
//            return false;
//        }
//    }
//
//
//    public Optional<String> getCountryCodeByAlpha2Code(String codeOfCountryByAlpha2) {
//        return Optional.of(new CountryCodeToRegionCodeMap().getCountryCodeToRegionCodeMap()
//                .entrySet()
//                .stream()
//                .filter(x -> x.getValue().contains(codeOfCountryByAlpha2))
//                .map(Map.Entry::getKey)
//                .findFirst()
//                .map(Object::toString)
//                .get());
//
//    }
//
//
//    public boolean isValidCountry(String codeOfCountryByAlpha2) {
//        CountryCodeToRegionCodeMap countryCodeToRegionCodeMap = new CountryCodeToRegionCodeMap();
//        return countryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap()
//                .values()
//                .stream()
//                .anyMatch(x -> x.contains(codeOfCountryByAlpha2));
//    }
//
//
//}
