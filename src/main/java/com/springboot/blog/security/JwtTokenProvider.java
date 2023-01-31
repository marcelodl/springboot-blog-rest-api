package com.springboot.blog.security;

import com.springboot.blog.config.security.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirationDate;

     public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Instant now = Instant.now();
        Instant expiration = now.plusMillis(Long.valueOf(jwtExpirationDate));

        String token = Jwts.builder()
                 .setSubject(username)
                 .setIssuedAt(Date.from(now))
                 .setExpiration(Date.from(expiration))
                 .signWith(key())
                 .compact();

        return token;
    }

    private Key key() {
         return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
         Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            return username;
    }

    public boolean validateToken(String token) {
        try {
           Jwts.parserBuilder()
                   .setSigningKey(key())
                   .build()
                   .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT Claims string is empty");
        }

    }


}
