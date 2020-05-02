package com.blesk.userservice.Validator;

import javax.validation.*;

import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Service.Genders.GendersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ContainsValidator implements ConstraintValidator<Contains, String> {

    private GendersServiceImpl gendersService;

    @Autowired
    public ContainsValidator(GendersServiceImpl gendersService) {
        this.gendersService = gendersService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return value != null && gendersService.findGenderByName(value) != null;
        } catch (UserServiceException ex) {
            return false;
        }
    }
}