package com.qss.adddvert.advert.model;

import com.qss.adddvert.advert.model.validation.PhoneNumberConstraint;
import lombok.*;

import javax.validation.constraints.Size;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PhoneNumber {
    @Getter @PhoneNumberConstraint
    private String number;
    @Getter
    private String description;

    public static PhoneNumber create(String number) {
        if (number.length() > 6) {
            return new PhoneNumber(number, "Mobile");
        } else {
            return new PhoneNumber(number, "Home");
        }
    }
}
