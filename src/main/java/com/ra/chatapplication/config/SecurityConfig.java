package com.ra.chatapplication.config;

import com.ra.chatapplication.utils.CustomAuthenticationFailureHandler;
import com.ra.chatapplication.utils.CustomAuthenticationSuccessHandler;
import com.ra.chatapplication.utils.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable() // for test only
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login/**").permitAll()
//                .antMatchers("/authenticate").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN") // Role-based authorization
//                .antMatchers("/chat/**").hasRole("USER")
                .antMatchers("/chat/**").permitAll()
//                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
//                .loginProcessingUrl("/authenticate").permitAll()
//                .defaultSuccessUrl("/login/check", true)
                .successForwardUrl("/login/check")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
            .logout()
                .logoutUrl("/login/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
            .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login?expired");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
//
//
//    @Autowired
//    private CustomAuthenticationFailureHandler authenticationFailureHandler;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/login/**").permitAll()
//                .antMatchers("/admin/**").permitAll()
////                .antMatchers("/chat/**").permitAll()
////                .antMatchers("/user/**").permitAll()
//                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html",
//                        "/webjars/**", "/swagger-resources/configuration/ui",
//                        "/swagger-resources/configuration/security").permitAll()
//                .antMatchers("/api/doc.html", "/api/**", "/doc.html").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
////                .successForwardUrl("/admin/user-list")
//                .failureHandler(authenticationFailureHandler)
//                .successHandler(authenticationSuccessHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//        // Disable CSRF protection for Swagger related URLs
//        http.csrf().ignoringAntMatchers(
//                "/v2/api-docs",
//                "/configuration/ui",
//                "/swagger-resources",
//                "/configuration/security",
//                "/swagger-ui.html",
//                "/doc.html",
//                "/webjars/**",
//                "/swagger-resources/configuration/ui",
//                "/swagger-resources/configuration/security"
//        );
//    }
//}
