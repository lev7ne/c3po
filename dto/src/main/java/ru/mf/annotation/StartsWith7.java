package ru.mf.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = StartsWith7Validator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartsWith7 {
    String message() default "Must start with 7 and be 9 digits long!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
