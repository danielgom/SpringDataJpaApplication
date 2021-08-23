package com.java.springdatajpaapplication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JWTProvider {

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        return JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("roles", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public String refreshToken(Authentication authentication) {

        User principal = (User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        return JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(Date.from(Instant.now().plus(3, ChronoUnit.HOURS)))
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("roles", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

}
