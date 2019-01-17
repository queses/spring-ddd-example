package com.qss.adddvert.advert.model;

import com.qss.adddvert.core.util.ArrayListFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AdContact {
    @Getter @Valid
    private List<PhoneNumber> phoneNumbers;
    @Getter
    private String email;

    public AdContact(PhoneNumber phoneNumber, String email) {
        this(ArrayListFactory.from(phoneNumber), email);
    }

    public AdContact(Collection<PhoneNumber> phoneNumbers, String email) {
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
        this.email = email;
    }

    public AdContact addPhone (PhoneNumber phoneNumber) {
        return new AdContact(ArrayListFactory.clone(this.phoneNumbers, phoneNumber), this.email);
    }
}
