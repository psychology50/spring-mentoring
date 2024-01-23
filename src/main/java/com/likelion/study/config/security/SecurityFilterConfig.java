package com.likelion.study.config.security;

import com.likelion.study.security.authentication.CustomUserDetails;
import com.likelion.study.security.authentication.UserDetailServiceImpl;
import com.likelion.study.security.filter.JwtAuthenticationFilter;
import com.likelion.study.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final JwtUtil accessJwtUtil;
    private final UserDetailServiceImpl customUserDetails;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(accessJwtUtil, customUserDetails);
    }
}
