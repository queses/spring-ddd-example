package com.qss.adddvert.core.test.fixture;

public interface Fixture {
    String dataPath();

    default Fixture[] depends() {
        return new Fixture[0];
    };
}
