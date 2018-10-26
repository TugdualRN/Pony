package com.pony.views.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, IPasswordMatch> { 
     
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {       
    }

    @Override
    public boolean isValid(IPasswordMatch viewModel, ConstraintValidatorContext context){   
        return viewModel.getPassword().equals(viewModel.getConfirmPassword());    
    }     
}