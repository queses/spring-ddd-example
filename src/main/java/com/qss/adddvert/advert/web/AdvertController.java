package com.qss.adddvert.advert.web;

import com.qss.adddvert.advert.model.*;
import com.qss.adddvert.advert.repo.AdvertRepo;
import com.qss.adddvert.core.util.ValidationExceptionDto;
import com.qss.adddvert.core.util.ValidationExceptionPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class AdvertController {
    @Autowired
    AdvertRepo advertRepo;
    @Autowired
    ValidationExceptionPresenter validationExceptionPresenter;

    @GetMapping("/advert/test")
    public Iterable<Advert> testList(Pageable pageable) {
        return advertRepo.findAll(pageable).getContent();
    }

    @PostMapping("/advert/test")
    public Advert testCreate(@RequestBody Advert advert) throws Exception {
        advertRepo.save(advert);
        return advert;
    }

    @PostMapping(value = "/advert/test/add-phone")
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

    private Advert createDefaultAdvert(String title) throws Exception {
        return new Advert(
                title,
                new AdContact(new PhoneNumber("89825365088", "Main phone"), "contact@me.com"),
                new AdPrice(33.33, PriceLiquid.create(2))
        );
    }
}
