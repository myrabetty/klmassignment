package com.afkl.travel.exercise.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security configuration.
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${rest.defaultUsername}")
    private String defaultUsername;

    @Value("${rest.defaultPassword}")
    private String defaultPassword;

    @Value("${admin.defaultUsername}")
    private String adminUsername;

    @Value("${admin.defaultPassword}")
    private String adminPassword;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/info").permitAll()
                .antMatchers("/actuator/metrics").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .httpBasic();
    }

    /**
     * Default user and admin user loaded in memory
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser(defaultUsername)
                .password(encoder.encode(defaultPassword))
                .roles("USER").and()
                .withUser(adminUsername)
                .password(encoder.encode(adminPassword))
                .roles("ADMIN");


    }
}
