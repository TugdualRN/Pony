package com.pony.utils.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MailValidator.class)
@Documented
public @interface ValidMail {   
    String message() default "Field is not a valid Email";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}