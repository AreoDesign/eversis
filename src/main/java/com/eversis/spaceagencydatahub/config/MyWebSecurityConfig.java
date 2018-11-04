package com.eversis.spaceagencydatahub.config;

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

    private static final String MANAGER = "MANAGER";
    private static final String CUSTOMER = "CUSTOMER";
    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    public MyBasicAuthenticationEntryPoint getMyBasicAuthenticationEntryPoint() {
        return new MyBasicAuthenticationEntryPoint();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("customer").password(encoder.encode("customer")).roles(CUSTOMER)    //TODO: move to enum type
            .and()
            .withUser("manager").password(encoder.encode("manager")).roles(MANAGER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/readme").permitAll()     //TODO: add controller for readme
            .regexMatchers(HttpMethod.POST, "/missions").hasRole(MANAGER)
            //TODO: add regexes for all controller methods
            .and()
            .httpBasic()
            .authenticationEntryPoint(getMyBasicAuthenticationEntryPoint())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
