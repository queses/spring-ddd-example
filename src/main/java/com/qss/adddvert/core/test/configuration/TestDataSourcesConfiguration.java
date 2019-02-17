package com.qss.adddvert.core.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
public class TestDataSourcesConfiguration {
    @Autowired
    private Environment env;

    @Bean
    @Profile("test")
    public DataSource dataSource () {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.getTestDatasourceEnvProp("driver-class-name"));
        dataSource.setUrl(this.getTestDatasourceEnvProp("url"));
        dataSource.setUsername(this.getTestDatasourceEnvProp("username"));
        dataSource.setPassword(this.getTestDatasourceEnvProp("password"));

        return dataSource;
    }

    private String getTestDatasourceEnvProp(String key) {
        return this.env.getProperty("spring.test-datasource." + key);
    }
}
