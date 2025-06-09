package net.qsmoke.team.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String jwtSecret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970\n";
    private final long jwtExpirationMs = 86400000; // 1 ngày

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("JWT Error: " + e.getMessage());
            return false;
        }
    }

    /** Parse the token’s “exp” claim and return it as an Instant */
    public Instant getExpiryFromToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        Date exp = claims.getExpiration();
        return exp.toInstant();
    }
}
