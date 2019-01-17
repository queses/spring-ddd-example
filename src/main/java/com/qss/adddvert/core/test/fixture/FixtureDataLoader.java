package com.qss.adddvert.core.test.fixture;

public interface FixtureDataLoader {
    void loadData(Fixture fixture) throws Exception;
    void unloadData(Fixture fixture) throws Exception;
}
