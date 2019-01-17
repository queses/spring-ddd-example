package com.qss.adddvert.advert.model;

import com.qss.adddvert.advert.fixtures.AdvertFixture;
import com.qss.adddvert.advert.repo.AdvertRepo;
import com.qss.adddvert.core.test.fixture.FixtureLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertTest {
    @Autowired
    private AdvertRepo advertRepo;
    @Autowired
    private FixtureLoader fixtureLoader;
    @Autowired
    private AdvertFixture advertFixture;

    @Before
    public void loadFixtures() throws Exception {
        fixtureLoader.load(advertFixture);
    }

    @After
    public void unloadFixtures() throws Exception {
        fixtureLoader.unload(advertFixture);
    }

    @Test
    public void advertShouldBePersisted() throws Exception {
        Advert advert = createDefaultAdvert();
        advertRepo.save(advert);

        assertThat(advertRepo.count()).isGreaterThan(0);
    }

    @Test
    public void phoneNumShouldBeValidated() throws Exception {
        Advert advert = createDefaultAdvert();
        AdContact oldContact = advert.getContact();
        advert.setContact(oldContact.addPhone(new PhoneNumber("33221")));
        assertThatThrownBy(() -> advertRepo.save(advert)).isInstanceOf(TransactionSystemException.class);

        advert.setContact(oldContact.addPhone(new PhoneNumber("332211")));
        assertThatCode(() -> advertRepo.save(advert)).doesNotThrowAnyException();
    }

    @Test
    public void priceShouldBeValidated() throws Exception {
        Advert advert = createDefaultAdvert();
        advert.addPrice(new AdPrice(-1, PriceLiquid.create(2)));
        assertThatThrownBy(() -> advertRepo.save(advert)).isInstanceOf(TransactionSystemException.class);
    }

    @Test
    public void priceLiquidShouldBeValidated() throws Exception {
        Advert advert = createDefaultAdvert();
        advert.addPrice(new AdPrice(33.3, PriceLiquid.create(-3)));
        assertThatThrownBy(() -> advertRepo.save(advert)).isInstanceOf(TransactionSystemException.class);
    }

    @Test
    public void titleShouldNotBeShort() throws Exception {
        Advert advert = createDefaultAdvert("Tiny");
        assertThat(advert.getTitle()).isEqualTo("Tiny");
        assertThatThrownBy(() -> advertRepo.save(advert)).isInstanceOf(TransactionSystemException.class);
    }

    private Advert createDefaultAdvert() throws Exception {
        return createDefaultAdvert("Supa dupa coooooooooooooool advert");
    }

    private Advert createDefaultAdvert(String title) throws Exception {
        return new Advert(
                title,
                new AdContact(new PhoneNumber("89825365055", "Main phone"), "contact@me.com"),
                new AdPrice(33.33, PriceLiquid.create(2))
        );
    }
}
