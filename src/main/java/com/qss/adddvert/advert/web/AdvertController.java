package com.qss.adddvert.advert.web;

import com.qss.adddvert.advert.model.*;
import com.qss.adddvert.advert.repo.AdvertRepository;
import com.qss.adddvert.core.util.ValidationExceptionDto;
import com.qss.adddvert.core.util.ValidationExceptionPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
public class AdvertController {
    @Autowired
    AdvertRepository advertRepo;
    @Autowired
    ValidationExceptionPresenter validationExceptionPresenter;

    @GetMapping("/api/dummy/advert")
    public Iterable<Advert> testList(Pageable pageable) {
        return advertRepo.findAll(pageable).getContent();
    }

    @PostMapping("/api/dummy/advert")
    public Advert testCreate(@RequestBody Advert advert) throws Exception {
        advertRepo.save(advert);
        return advert;
    }

    @PostMapping(value = "/api/dummy/advert/add-phone")
    public PhoneNumber testAddPhone(@RequestBody PhoneNumber phoneNumber) throws Exception {
        return phoneNumber;
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ValidationExceptionDto> onException(TransactionSystemException ex, WebRequest request) {
        return new ResponseEntity<>(
                validationExceptionPresenter.getMessages(ex),
                HttpStatus.BAD_REQUEST
        );
    }
}
