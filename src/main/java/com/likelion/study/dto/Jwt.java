package com.likelion.study.dto;

/**
 * Jwt Dto
 * @param accessToken String
 * @param refreshToken String
 */
public record Jwt(
        String accessToken,
        String refreshToken
) {
    public static Jwt of(String accessToken, String refreshToken) {
        return new Jwt(accessToken, refreshToken);
    }
}
