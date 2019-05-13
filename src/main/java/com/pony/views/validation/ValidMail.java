package com.pony.views.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MailValidator.class)
@Documented
public @interface ValidMail {   
    String message() default "Field is not a valid Email";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}