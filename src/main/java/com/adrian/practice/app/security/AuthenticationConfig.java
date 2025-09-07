package com.adrian.practice.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.adrian.practice.app.security.filters.AuthenticationFilter;
import com.adrian.practice.app.security.filters.AuthorizationFilter;

/**
 * Configuration class for setting up authentication and security filters.
 * This class defines beans for the AuthenticationManager and SecurityFilterChain.
 * It configures HTTP security to require authentication for some requests
 * @author AdrianSA
 * @since 2025-09
 * @version 1.0
 */
@Configuration
public class AuthenticationConfig {
    private final AuthenticationConfiguration authenticationConfiguration;

    /**
     * Constructor for AuthenticationConfig.
     * @param authenticationConfiguration
     */
    public AuthenticationConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    /**
     * Creates and returns an AuthenticationManager bean.
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates and returns a PasswordEncoder bean using BCrypt.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and returns a SecurityFilterChain bean.
     * @param httpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
            auth -> auth.anyRequest().authenticated())
        .addFilter( new AuthenticationFilter(authenticationManager()))
        .addFilter( new AuthorizationFilter(authenticationManager()))
        .csrf(csrfCustomizer -> csrfCustomizer.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   
        .build();
    }

}
