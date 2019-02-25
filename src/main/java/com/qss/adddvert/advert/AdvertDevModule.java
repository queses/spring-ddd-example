package com.qss.adddvert.advert;

import com.qss.adddvert.advert.model.*;
import com.qss.adddvert.advert.repo.AdvertRepository;
import com.qss.adddvert.core.ModuleRegisterer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class AdvertDevModule implements ModuleRegisterer {
    @Autowired
    private AdvertRepository advertRepo;

    @Override
    public void register() {
//        initDb();
    }

    @Transactional
    private void initDb() {
        this.advertRepo.save(new Advert(
                "Cool jedi lighsaber",
                new AdContact(PhoneNumber.create("332211"), "first@mail.com"),
                new AdPrice(33.2, PriceLiquid.create(PriceLiquidValue.GREAT))
        ));

        this.advertRepo.save(new Advert(
                "iPhone 3G, almost new",
                new AdContact(PhoneNumber.create("89321234321"), "second@mail.com"),
                new AdPrice(44.2, PriceLiquid.create(PriceLiquidValue.NORMAL))
        ));
    }
}
