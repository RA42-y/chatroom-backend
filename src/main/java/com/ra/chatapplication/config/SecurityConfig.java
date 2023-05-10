package com.ra.chatapplication.config;

import com.ra.chatapplication.utils.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successForwardUrl("/admin/user-list")
//                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

}
