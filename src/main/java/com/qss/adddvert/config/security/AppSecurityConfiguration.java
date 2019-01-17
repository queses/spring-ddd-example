package com.qss.adddvert.config.security;

import com.qss.adddvert.core.ModuleWebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${spring.h2.console.path}")
    private String h2Path;
    @Autowired
    private List<ModuleWebSecurityConfig> moduleConfigs;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().anyRequest().authenticated().and()
                .formLogin().loginPage("/login").permitAll().and()
                .logout().logoutUrl("/logout").permitAll();

        this.allowH2Console(httpSecurity);
        this.configureModules(httpSecurity);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();

        users.add(User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build()
        );

        users.add(User.builder()
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER")
                .build()
        );

        return new InMemoryUserDetailsManager(users);
    }

    private void configureModules(HttpSecurity httpSecurity) throws Exception {
        for (ModuleWebSecurityConfig moduleConfig : moduleConfigs) {
            moduleConfig.configure(httpSecurity);
        }
    }

    private void allowH2Console(HttpSecurity httpSecurity) throws Exception {
        String h2Wildcard = this.h2Path.concat("/**");

        httpSecurity.authorizeRequests().antMatchers(h2Wildcard).permitAll();

        httpSecurity.csrf().ignoringAntMatchers(h2Wildcard);
        httpSecurity.headers().frameOptions().disable();
    }
}
