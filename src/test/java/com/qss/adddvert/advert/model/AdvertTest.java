package com.qss.adddvert.advert.model;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.qss.adddvert.advert.repo.AdvertRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.TransactionSystemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DatabaseSetup("/fixtures/advert/advert.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AdvertTest {
    @Autowired
    private AdvertRepository advertRepo;

    @Test
    public void advertShouldBeCreated() throws Exception {
        Advert advert = this.createDefaultAdvert("Great advert but with no name");
        assertThat(advert.getTitle()).isEqualTo("Great advert but with no name");
    }

    @Test
    public void advertShouldBePersisted() throws Exception {
        Advert advert = createDefaultAdvert();
        advertRepo.save(advert);

        assertThat(advertRepo.count()).isGreaterThan(0);
    }

    @Test
    public void manyAdvertsShouldBePersisted() throws Exception {
        Advert advert1 = createDefaultAdvert("Advert to save, number one");
        advertRepo.save(advert1);
        Advert advert2 = createDefaultAdvert("Advert to save, number two");
        advertRepo.save(advert2);
        Advert advert3 = createDefaultAdvert("Advert to save, number three");
        advertRepo.save(advert3);

        assertThat(advertRepo.count()).isGreaterThan(2);
    }

    @Test
    public void phoneNumShouldBeValidated() throws Exception {
        Advert advert = createDefaultAdvert();
        AdContact oldContact = advert.getContact();
        advert.setContact(oldContact.addPhone(PhoneNumber.create("33221")));
        assertThatThrownBy(() -> advertRepo.save(advert)).isInstanceOf(TransactionSystemException.class);

        advert.setContact(oldContact.addPhone(PhoneNumber.create("332211")));
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
