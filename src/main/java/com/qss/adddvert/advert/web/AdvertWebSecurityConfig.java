package com.qss.adddvert.advert.web;

import com.qss.adddvert.core.ModuleWebSecurityConfig;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class AdvertWebSecurityConfig implements ModuleWebSecurityConfig {
    private String basePath = "/api/advert";

    @Override
    public void configure(HttpSecurity http) throws Exception {
    }
}
