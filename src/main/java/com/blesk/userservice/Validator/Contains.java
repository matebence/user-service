package com.blesk.userservice.Validator;

import com.blesk.userservice.Value.Messages;

import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = ContainsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Contains {

    public String message() default Messages.CONTAINS_VALIDATOR_DEFAULT;

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}