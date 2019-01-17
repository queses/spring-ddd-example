package com.qss.adddvert.core.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Service
public class ValidationExceptionPresenter {
    public ValidationExceptionDto getMessages (TransactionSystemException ex) {
        ConstraintViolationException exception = (ConstraintViolationException)ex.getMostSpecificCause();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        ValidationExceptionDto errors = new ValidationExceptionDto();
        for (ConstraintViolation<?> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return errors;
    }
}
