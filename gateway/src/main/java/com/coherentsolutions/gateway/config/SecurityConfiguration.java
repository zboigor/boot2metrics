package com.coherentsolutions.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/actuator/health", "/actuator/info", "/actuator/prometheus")
                    .permitAll()
                .antMatchers("/actuator/**")
                    .hasRole("ACTUATOR")
                .anyRequest()
                    .permitAll()
                .and()
                .httpBasic();
    }
}