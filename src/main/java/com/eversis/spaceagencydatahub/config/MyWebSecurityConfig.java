package com.eversis.spaceagencydatahub.config;

import com.eversis.spaceagencydatahub.dictionary.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    public MyBasicAuthenticationEntryPoint getMyBasicAuthenticationEntryPoint() {
        return new MyBasicAuthenticationEntryPoint();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(Role.CUSTOMER.getRoleName()).password(encoder.encode(Role.CUSTOMER.getPassword())).roles(Role.CUSTOMER.getRoleName())    //TODO: move to enum type
            .and()
            .withUser(Role.MANAGER.getRoleName()).password(encoder.encode(Role.MANAGER.getPassword())).roles(Role.MANAGER.getRoleName());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/readme").permitAll()     //TODO: add controller for readme
            .regexMatchers(HttpMethod.POST, "/missions").hasRole(Role.MANAGER.getRoleName())
            //TODO: add regexes for all controller methods
            .and()
            .httpBasic()
            .authenticationEntryPoint(getMyBasicAuthenticationEntryPoint())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
