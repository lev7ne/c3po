package ru.mf.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = StartsWith62Validator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartsWith62 {
    String message() default "Must start with 62 and be 10 digits long!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}