package com.d3banking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .httpBasic()
            .and()
                .authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/**").authenticated()
            .and()
                .csrf().ignoringAntMatchers("/actuator/**");
        // @formatter:on
    }
}
