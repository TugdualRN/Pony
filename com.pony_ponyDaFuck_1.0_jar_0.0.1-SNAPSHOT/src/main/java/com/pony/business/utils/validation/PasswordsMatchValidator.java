package com.pony.business.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pony.viewmodels.RegisterViewModel;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> { 
     
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {       
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
        RegisterViewModel viewModel = (RegisterViewModel) obj;
        return viewModel.getPassword().equals(viewModel.getConfirmPassword());    
    }     
}