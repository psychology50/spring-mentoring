package com.likelion.study.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Primary
@Slf4j
public class AccessJwtUtil implements JwtUtil {
    private final Key signatureKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final Duration tokenExpiration; // 3 * 60 * 1000 ms = 3 min

    public AccessJwtUtil() {
        String jwtSecretKey = "lkgawjhreflkawehfaklwjedfnlkawdnfalksadfalwesjkhawe";
        final byte[] secretKeyBytes = Base64.getDecoder().decode(jwtSecretKey);
        this.signatureKey = Keys.hmacShaKeyFor(secretKeyBytes);
        this.tokenExpiration = Duration.of(18000000, ChronoUnit.MILLIS); // 30m ~ 1hr
    }

    @Override
    public String generateToken(Long userId) {
        final Date now = new Date();

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(userId))
                .signWith(signatureKey, signatureAlgorithm)
                .setExpiration(createExpireDate(now, tokenExpiration.toMillis()))
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimFromToken(token);
        return claims.get("userId", Long.class);
    }

    private Claims getClaimFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signatureKey).build() // 파싱 도구 생성
                    .parseClaimsJws(token) // 파싱
                    .getBody(); // 토큰의 몸통 반환
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
            throw new SignatureException("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Expired JWT token");
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }

    // 머리 만들기
    private Map<String, Object> createHeader() {
        return Map.of("typ", "JWT",
                "alg", "HS256",
                "regDate", System.currentTimeMillis());
    }

    // 몸통 만들기
    private Map<String, Object> createClaims(Long userId) {
        return Map.of("userId", userId);
    }

    private Date createExpireDate(final Date now, long expirationTime) {
        return new Date(now.getTime() + expirationTime);
    }
}

