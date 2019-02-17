package com.qss.adddvert.config.security;

import com.qss.adddvert.auth.web.JwtAuthenticationFilter;
import com.qss.adddvert.auth.web.JwtAuthorizationFilter;
import com.qss.adddvert.core.ModuleWebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        this.allowH2Console(httpSecurity);

        httpSecurity
                .csrf().ignoringAntMatchers("/api/**").and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/api/login").permitAll()
                    .anyRequest().authenticated()
                .and()
                .addFilterAfter(
                        new JwtAuthenticationFilter(authenticationManager(), "/api/login"),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilter(new JwtAuthorizationFilter(authenticationManager()));

        this.configureModules(httpSecurity);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();

        users.add(User.builder()
                .username("user")
                .password(bCryptPasswordEncoder().encode("password"))
                .roles("USER")
                .build()
        );

        users.add(User.builder()
                .username("admin")
                .password(bCryptPasswordEncoder().encode("password"))
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
