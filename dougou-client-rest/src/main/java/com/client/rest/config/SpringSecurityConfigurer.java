package com.client.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {
    JsonUsernamePasswordAuthenFilter authenFilter;
    public SpringSecurityConfigurer(){
        this.authenFilter = new JsonUsernamePasswordAuthenFilter();
    }
    @Autowired @Lazy
    JsonUsernamePasswordAuthenFilter jsonUsernamePasswordAuthenFilter;
    @Autowired @Lazy
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailService userDetailService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().and().formLogin().and().csrf().disable();
        http.addFilterAt(jsonUsernamePasswordAuthenFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    JsonUsernamePasswordAuthenFilter jsonUsernamePasswordAuthenFilter() throws Exception {
       JsonUsernamePasswordAuthenFilter jsonUsernamePasswordAuthenFilter = new JsonUsernamePasswordAuthenFilter();
       jsonUsernamePasswordAuthenFilter.setAuthenticationManager(this.authenticationManager);
        return  jsonUsernamePasswordAuthenFilter;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

}
