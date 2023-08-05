package com.news.projectsmanagement.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.news.projectsmanagement.model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {

    private static final String privateKeyJWT = "secretKey";

    public String generateToken(Authentication authentication) {

        int expirationTime = (24 * 60 * 60 * 1000);

        Date expirationDate = new Date(new Date().getTime() + expirationTime);

        Users user = (Users) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, privateKeyJWT)
                .compact();
    }

    public Optional<Long> getUserId(String token) {

        try {
            Claims claims = parse(token).getBody();

            return Optional.ofNullable(Long.parseLong(claims.getSubject()));

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(privateKeyJWT).parseClaimsJws(token);
    }
}
