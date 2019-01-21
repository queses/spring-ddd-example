package com.qss.adddvert.auth.web;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationManager authenticationManager;
    private AuthLoginForm form;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String antPattern) {
        super(new AntPathRequestMatcher(antPattern, "POST"));
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException {
        try {
            form = new ObjectMapper().readValue(req.getInputStream(), AuthLoginForm.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("rememberMe", form.rememberMe);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.username, form.password, new ArrayList<>())
        );
    }

    // @todo нормально реализовать функцию "запомнить меня" на JWT
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) {
        boolean rememberMe = (boolean)req.getAttribute("rememberMe");
        long expTime = (rememberMe) ? SecurityConstans.EXPIRATION_TIME : 0;

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstans.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstans.SECRET.getBytes()));

        res.addHeader(SecurityConstans.HEADER_STRING, SecurityConstans.TOKEN_PREFIX + token);
    }
}
