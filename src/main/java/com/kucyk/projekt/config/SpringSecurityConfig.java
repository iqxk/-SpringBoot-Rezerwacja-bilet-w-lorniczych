package com.kucyk.projekt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/resources/**", "/static/**","/webjars/**","/images/**").permitAll()
                .antMatchers("/login", "/register", "/routes/**").permitAll()
                //.antMatchers("/h2-console/**").hasRole("ADMIN") // Potem zmienić na dostęp przez admina
                //.antMatchers("/games/add", "/games/edit", "games/doForm").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated();
        http
                .formLogin()
                .loginPage("/login")
                //.successForwardUrl("/index")
                .permitAll();

        http.logout().permitAll();
        http.exceptionHandling().accessDeniedPage("/error403");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    // Tymczasowo póki nie zrobię userów
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

