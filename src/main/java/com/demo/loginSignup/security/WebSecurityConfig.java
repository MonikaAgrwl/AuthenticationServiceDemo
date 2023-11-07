package com.demo.loginSignup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class WebSecurityConfig {

    //TODO Autowire password encoder

    //TODO Implement JWT functionality
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Added to disable cross site request forgery
        http.csrf(csrf ->csrf.disable());
        //Added to remove the problem of H2 console not appearing in the frame embedded in browser
        http.headers(headers ->headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        return http.build();
    }

}
