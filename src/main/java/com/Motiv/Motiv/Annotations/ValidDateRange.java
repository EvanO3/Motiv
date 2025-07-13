package com.Motiv.Motiv.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.Motiv.Motiv.Validators.DateRangeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=DateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "Start date must be before the end date";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
    
}
