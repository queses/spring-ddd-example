package com.qss.adddvert.core.test.fixture;

import com.qss.adddvert.core.errors.InvalidArgumentException;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LiquibaseDataFixtureLoader implements FixtureDataLoader {
    @Autowired
    private DataSource dataSource;

    private Map<String, String> contexts = new HashMap<>();
    private Map<String, Liquibase> bases = new HashMap<>();

    public void loadData(Fixture fixture) throws SQLException, LiquibaseException {
        String newContext = Double.toString(Math.random());

        Liquibase liquibase = new Liquibase(
                fixture.dataPath(),
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(dataSource.getConnection())
        );

        liquibase.update(newContext);

        String fixtureClassName = fixture.getClass().getName();
        contexts.put(fixtureClassName, newContext);
        bases.put(fixtureClassName, liquibase);
    }

    public void unloadData(Fixture fixture) throws LiquibaseException, InvalidArgumentException {
        String fixtureClassName = fixture.getClass().getName();
        String context = contexts.get(fixtureClassName);
        Liquibase base = bases.get(fixtureClassName);
        if (context == null || base == null) {
            throw new InvalidArgumentException("Not loaded fixture passed to data unload");
        }

        base.rollback(1, context);
    }
}
