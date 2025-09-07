package com.adrian.practice.app.security.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.adrian.practice.app.entities.User;
import com.adrian.practice.app.security.SecurityConfigParams;
import static com.adrian.practice.app.security.SecurityConfigParams.CONTENT_TYPE;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


/**
 * Security filter class that handles the system authentication
 * creating a JWT for access
 * @author AdrianSA
 * @since 2025-09
 * @version 1.0
 */
@Log4j2
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Reads an object from the request and create an Authentication
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @return Authentication
     * @throws AuthenticationException
     * 
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user;
        String username = null;
        String password = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            log.error("Error reading user from request", e);
        }   

        UsernamePasswordAuthenticationToken authenticationToken =
             new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Response the request with an 401 Error when the authentication fails
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @param AuthenticationException faile
     * @throws IOException, ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        
        Map<String, Object> body = Map.of(
            "error", "Authentication failed",
            "message", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }

    /**
     * Response the request with a JsonWebToken when the authentication is successful
     * @param HttpServletRequest request
     * @param HttpServletResponse
     * @FilterChain chain
     * @Authentication authResult
     * @throws IOException, ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((org.springframework.security.core.userdetails.User) 
                                    authResult.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        
        
        Claims claims = Jwts.claims()
            .add("username", username)
            .add("authorities", new ObjectMapper().writeValueAsString(authorities))
            .build();

        String token = Jwts.builder()
            .subject(username)
            .claims(claims)
            .signWith(SecurityConfigParams.SECRET_KEY)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
            .compact();

        Map<String, Object> body = Map.of(
            "username", username,
            "token", token);


        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }


    
}
