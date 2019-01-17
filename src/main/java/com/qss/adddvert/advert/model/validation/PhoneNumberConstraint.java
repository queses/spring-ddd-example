package com.qss.adddvert.advert.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidation.class)
@Documented
public @interface PhoneNumberConstraint {
    String message() default "{advert.PhoneNumber}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
