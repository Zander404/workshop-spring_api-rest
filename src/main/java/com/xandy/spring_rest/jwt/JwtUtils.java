package com.xandy.spring_rest.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARED = "Bearer ";
    public static final String JWT_STRING = "Authorization";
    public static final String SECRET_KEY = "secret";
    public static final long EXPIRED_DAYS = 0;
    public static final long EXPIRED_HOURS = 0;
    public static final long EXPIRED_MINUTES = 2;

    private JwtUtils() {

    }

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpiredDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRED_DAYS).plusHours(EXPIRED_HOURS).plusMinutes(EXPIRED_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String createJWT(String username, String role) {
        Date issuedAt = toExpiredDate(new Date());
        Date limit = Date.from(issuedAt.toInstant());

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(limit)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token);
    }


    public static Claims getClaimsFromToken(String token) {
        try {

            return Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws().getBody();
        } catch (JwtException e) {
            log.error(String.format("Invalid JWT token: %s", token), e);

        }
    }


}
