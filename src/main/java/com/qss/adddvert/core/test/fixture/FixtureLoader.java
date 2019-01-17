package com.qss.adddvert.core.test.fixture;

public interface FixtureLoader {
    void load(Fixture fixture) throws Exception;
    void unload(Fixture fixture) throws Exception;
}
