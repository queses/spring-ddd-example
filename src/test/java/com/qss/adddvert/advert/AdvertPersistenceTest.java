package com.qss.adddvert.advert;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.qss.adddvert.advert.fixtures.AdvertFixture;
import com.qss.adddvert.advert.repo.AdvertRepo;
import com.qss.adddvert.core.test.fixture.FixtureLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/fixtures/advert/advert.xml")
public class AdvertPersistenceTest {
    @Autowired
    private AdvertRepo advertRepo;

    @Test
    public void repoShouldCount() {
        assertThat(this.advertRepo.count()).isGreaterThanOrEqualTo(1);
    }
}
