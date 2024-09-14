package ru.mf.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StartsWith7Validator implements ConstraintValidator<StartsWith7, Long> {

    @Override
    public void initialize(StartsWith7 constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String valueAsString = value.toString();
        return valueAsString.matches("^7\\d{8}$");
    }

}
