package com.qss.adddvert.advert.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidation implements ConstraintValidator<PhoneNumberConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (
            s.matches("\\d{6}")
            || s.matches("\\d{11}")
        );
    }
}
