package com.humanResource.humanResource.springSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure security settings, endpoints, and authorization here
        http
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/private/**").authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt(); // For OAuth2 with JWT

        // For JWT implementation without OAuth2:
        // http
        //    .authorizeRequests()
        //        .antMatchers("/api/public/**").permitAll()
        //        .antMatchers("/api/private/**").authenticated()
        //    .and()
        //    .addFilter(/* Add JWT authentication filter */)
        //    .addFilterAfter(/* Add JWT authorization filter */, UsernamePasswordAuthenticationFilter.class);
    }
}

