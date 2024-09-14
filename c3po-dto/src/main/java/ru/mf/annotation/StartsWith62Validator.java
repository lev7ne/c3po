package ru.mf.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StartsWith62Validator implements ConstraintValidator<StartsWith62, Long> {

    @Override
    public void initialize(StartsWith62 constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String valueAsString = value.toString();
        return valueAsString.matches("^62\\d{8}$");
    }

}