package com.ktb.community.mapper;

import com.ktb.community.dto.TokenRefreshResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {

    public TokenRefreshResponseDto mapToTokenRefreshResponseDto(String newAccessToken) {
        return TokenRefreshResponseDto.builder()
                .accessToken(newAccessToken)
                .build();
    }
}
