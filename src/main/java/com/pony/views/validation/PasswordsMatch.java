package com.pony.views.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
public @interface PasswordsMatch { 
    String message() default "Passwords must match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}