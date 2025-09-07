package com.adrian.practice.app.security.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.adrian.practice.app.security.SecurityConfigParams.AUTHORITIES;
import static com.adrian.practice.app.security.SecurityConfigParams.CONTENT_TYPE;
import static com.adrian.practice.app.security.SecurityConfigParams.HEADER_AUTHORIZATION;
import static com.adrian.practice.app.security.SecurityConfigParams.PREFIX_TOKEN;
import static com.adrian.practice.app.security.SecurityConfigParams.SECRET_KEY;
import com.adrian.practice.app.utils.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
/**
 * Authorization filter that extends BasiAuthenticationFilter class to handler
 * the authorization token
 * @author AdrianSA
 * @version 1.0
 * @since 2025-09
 */
@Log4j2
public class AuthorizationFilter extends BasicAuthenticationFilter {


    public AuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    /**
     * Handle the authenticated request to validate the token
     * @param HttpServletRequest request
     * @param HttpServñetResponse response
     * @param FilterChain chain
     * @throws IOException, ServletException 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException{
        String header = request.getHeader(HEADER_AUTHORIZATION);

        //Validates the authorization header
        if(StringUtils.isEmpty(header) || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        } 
        
        //Get the token
        String token = header.replace(PREFIX_TOKEN , "");
        try {
            //Validates the token
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            //Gets the user roles
            Object authoritiesClaims = claims.get(AUTHORITIES);
            Collection< ? extends GrantedAuthority> authorities = Arrays.asList(
                new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                .readValue(
                    authoritiesClaims.toString().getBytes(), 
                    SimpleGrantedAuthority[].class
                )
            );
            //Set the authentication context to the user
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            log.error(e);
            Map<String,String> body = Map.of(
                "messagge", "Invalid Token",
                "error", e.getMessage()
            );

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}