package com.likelion.study.security.filter;

import com.likelion.study.security.authentication.UserDetailServiceImpl;
import com.likelion.study.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor // 생성자 만들어주는 어노테이션
@Slf4j // 로그 찍을 때
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // 사용자가 보낸 요청 (수정 불가능)
            @NonNull HttpServletResponse response, // 사용자한테 보낼 응답
            @NonNull FilterChain filterChain // 다음 필터로 넘어가는 것을 허용
    ) throws ServletException, IOException {
        // 1. header -> 로그인 유저 -> doFilter 무시
        String authHeader = request.getHeader("Authorization"); // authHeader ? Bearer <token> : null
        log.info("Authorization header: {}", authHeader);

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) { // 헤더 없으면 그냥 무시 -> 인가 처리 뒤에서 함
            log.info("Authorization header is not exist");
            filterChain.doFilter(request, response); // by-pass
            return ;
        }

        // 헤더 추출 -> 토큰 유효성 검사 -> SecurityUser 생성
        String accessToken = jwtUtil.resolveToken(authHeader);

        // Security가 관리하는 (1) User 클래스 생성해서, (2) Security한테 등록해줘야 함.
        UserDetails userDetails = getUserDetails(accessToken); // (1) UserDetails : Security User 만들기 위한 클래스
        authenticateUser(userDetails, request); // (2) Security User 등록

        filterChain.doFilter(request, response);
    }

    private UserDetails getUserDetails(final String accessToken) {
        Long userId = jwtUtil.getUserIdFromToken(accessToken);
        return userDetailServiceImpl.loadUserByUsername(userId.toString());
    }

    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        /* 이해하지 마세요! */
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication); // 등록시켜줬다!
        log.info("Authenticated user: {}", userDetails.getUsername());
    }
}
