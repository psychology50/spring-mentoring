package com.likelion.study.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

// JwtUtil이라면 마땅히 지켜야 할 명세서
public interface JwtUtil {
    // 3. 토큰 검증 -> 명세서에는 안 나타남

    // 1. 인가 헤더 파싱
    // request header "Authorization" 필드에서 문자열 가져옴.
    // or
    // Authorization: Bearer <token>
    /**
     * 헤더로부터 토큰을 추출하고 유효성을 검사하는 메서드
     * @param authHeader : 메시지 헤더
     * @return 값이 있다면 토큰, 없다면 빈 문자열 (빈 문자열을 반환하는 경우 예외 처리를 해주어야 한다.)
     */
    default String resolveToken(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring("Bearer ".length());
        }
        return "";
//        throw new IllegalArgumentException("Invalid Token");
    }

    // 2. 토큰 생성
    /**
     * 사용자 정보 기반으로 토큰을 생성하는 메서드
     * @param userId Long : 로그인 유저 pk
     * @return String : 토큰
     */
    String generateToken(Long userId);

    // 4. 토큰에서 유저 정보 추출
    /**
     * 토큰으로 부터 사용자 정보를 추출하여 로그인 유저 pk 반환하는 메서드
     * @param token String : 토큰
     * @return Long : 로그인 유저 pk
     */
    Long getUserIdFromToken(String token);
}
