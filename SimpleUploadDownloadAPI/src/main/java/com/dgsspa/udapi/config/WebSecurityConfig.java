package com.dgsspa.udapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
	@Bean
    public UserDetailsService userDetailsService() {
        // Aggiungi un utente in memoria per scopi dimostrativi
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("utente")
                        .password("password")
                        .roles("ADMIN")
                        .build()
        );
    }
 
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }
 

}
