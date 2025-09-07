package com.adrian.practice.app.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class SecurityConfigParams {
    public static final String CONTENT_TYPE = "application/json";
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String AUTHORITIES = "authorities";
}
