package com.qss.adddvert.advert.fixtures;

import com.qss.adddvert.core.test.fixture.Fixture;
import org.springframework.stereotype.Component;

@Component
public class AdvertFixture implements Fixture {
    @Override
    public String dataPath() {
        return "fixtures/advert/advert.xml";
    }
}
