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

@Order(1)
@EnableWebSecurity
@Configuration
public class AdminSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http    .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();*/

        http.antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/workers-control").hasRole("ADMIN")
                .antMatchers("/admin/departments-control").hasRole("ADMIN")
                .antMatchers("/admin/{userID}").access("@adminUserDetailsService.checkUserID(authentication, #userID)")


                .and()
                .formLogin()
                .loginPage("/admin-login.html")
                .loginProcessingUrl("/admin/login")
                .successHandler(new AdminAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin-login.html")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                .csrf().disable();


    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserDetailsService).passwordEncoder(passwordEncoder2());
    }
    @Bean
    public PasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

}
