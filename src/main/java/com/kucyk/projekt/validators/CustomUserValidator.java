package com.kucyk.projekt.validators;

import com.kucyk.projekt.models.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CustomUserValidator implements Validator
{
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "username", "Empty.user.model");
        ValidationUtils.rejectIfEmpty(errors, "password", "Empty.user.password");
        ValidationUtils.rejectIfEmpty(errors, "passwordConfirm", "Empty.user.passwordConfirm");

        var user = (User) target;
        if(!user.getPassword().equals(user.getPasswordConfirm())){
            errors.rejectValue( "passwordConfirm", "NotEqual.user.passwordConfirm");
        }
    }
}
