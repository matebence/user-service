package com.blesk.userservice.Validator;

import javax.validation.*;

import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Genders;
import com.blesk.userservice.Service.Genders.GendersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContainsValidator implements ConstraintValidator<Contains, String> {

    @Autowired
    private GendersServiceImpl gendersService;

    private static List<Genders> gendersList = new ArrayList<>();

    @Override
    public void initialize(Contains constraintAnnotation) {
        if (this.gendersService != null && ContainsValidator.gendersList.isEmpty())
            ContainsValidator.gendersList = this.gendersService.getAllGenders(0, -1);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result;
        try {
            result = value != null && !ContainsValidator.gendersList.stream().filter(line -> value.equals(line.getName())).collect(Collectors.toList()).isEmpty();
        } catch (UserServiceException ex) {
            result = false;
        }
        if (this.gendersService == null) ContainsValidator.gendersList = null;
        return result;
    }
}