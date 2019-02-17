package com.qss.adddvert.core.test.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Service
public class FixtureLoaderImpl implements FixtureLoader {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private FixtureDataLoader dataLoader;

    private Map<String, Integer> loaded = new HashMap<>();

    public void load(Fixture fixture) throws Exception {
        for (Fixture parentFixture : fixture.depends()) {
            load(parentFixture);
        }

        if (this.getFixtureLoadCount(fixture) == 0) {
            dataLoader.loadData(fixture);
        }

        this.incLoadCount(fixture);
    }

    public void unload(Fixture fixture) throws Exception {
        for (Fixture parentFixture : fixture.depends()) {
            unload(parentFixture);
        }

        this.decLoadCount(fixture);

        if (this.getFixtureLoadCount(fixture) == 0) {
            dataLoader.unloadData(fixture);
        }
    }

    private void incLoadCount(Fixture fixture) {
        int count = getFixtureLoadCount(fixture);
        this.loaded.put(fixture.getClass().getName(), count + 1);
    }

    private void decLoadCount(Fixture fixture) {
        int count = getFixtureLoadCount(fixture);
        if (count > 0) {
            this.loaded.put(fixture.getClass().getName(), count - 1);
        }
    }

    private int getFixtureLoadCount(Fixture fixture) {
        Integer count = loaded.get(fixture.getClass().getName());
        if (count == null) {
            return 0;
        } else {
            return count;
        }
    }
}
