package com.qss.adddvert.core;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface ModuleWebSecurityConfig {
    public void configure(HttpSecurity httpSecurity) throws Exception;
}
