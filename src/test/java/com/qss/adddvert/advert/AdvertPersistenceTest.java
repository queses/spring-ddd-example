package com.qss.adddvert.advert;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.qss.adddvert.advert.model.Advert;
import com.qss.adddvert.advert.model.PhoneNumber;
import com.qss.adddvert.advert.repo.AdvertRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/fixtures/advert/advert.xml")
public class AdvertPersistenceTest {
    @Autowired
    private AdvertRepository advertRepo;

    @Test
    public void repoShouldCount() {
        assertThat(this.advertRepo.count()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void repoShouldFindByTitle() {
        List<Advert> adverts = this.advertRepo.findByTitle("Very cool jedi blue lightsaber");
        assertThat(adverts.size()).isEqualTo(1);
        assertThat(adverts.get(0).getTitle()).isEqualTo("Very cool jedi blue lightsaber");
    }

    @Test
    public void repoShouldFindByContactEmail() {
        List<Advert> adverts = this.advertRepo.findByContactEmail("advert1@gmail.com");
        assertThat(adverts.size()).isGreaterThanOrEqualTo(1);
        assertThat(adverts.get(0).getContact().getEmail()).isEqualTo("advert1@gmail.com");
    }

    @Test
    public void repoShouldFindByContactPhone() {
        List<Advert> adverts = this.advertRepo.findByContactPhone("89825365055");
        assertThat(adverts.size()).isGreaterThanOrEqualTo(1);

        boolean hasPhone = false;
        for (PhoneNumber number : adverts.get(0).getContact().getPhoneNumbers()) {
            if (number.getNumber().equals("89825365055")) {
                hasPhone = true;
            }
        }
        assertThat(hasPhone).isTrue();
    }
}
