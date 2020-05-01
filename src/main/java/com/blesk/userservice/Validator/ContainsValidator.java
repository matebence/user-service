package com.blesk.userservice.Validator;

import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

public class ContainsValidator implements ConstraintValidator<Contains, String> {

//    @Autowired
//    private LibraryUserDetailService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
//        return value != null && !userService.isEmailAlreadyInUse(value);
        return true;
    }
}