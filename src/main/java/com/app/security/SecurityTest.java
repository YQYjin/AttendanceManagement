package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Order(2)
@EnableWebSecurity
@Configuration
public class SecurityTest extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http    .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();*/

        http
                .antMatcher("/user/**")

                .authorizeRequests()
                    .antMatchers("/user/{userID}").access("@myUserDetailsService.checkUserID(authentication, #userID)")
                    .antMatchers("/user/info/{userID}").access("@myUserDetailsService.checkUserID(authentication, #userID)")

                    .and()
                .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/user/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new CustomAuthenticationSuccessHandler())
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/userlogout")
                    .logoutSuccessUrl("/login")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                .csrf().disable();


    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
