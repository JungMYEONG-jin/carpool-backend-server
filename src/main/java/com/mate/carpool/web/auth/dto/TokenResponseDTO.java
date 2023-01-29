package com.mate.carpool.web.auth.dto;

import lombok.Getter;

@Getter
public class TokenResponseDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}
