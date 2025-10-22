package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.TokenRefreshRequestDto;
import com.ktb.community.dto.TokenRefreshResponseDto;
import com.ktb.community.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public DataResponseDto<TokenRefreshResponseDto> refreshAccessToken(@RequestBody TokenRefreshRequestDto tokenRefreshRequestDto) {
        TokenRefreshResponseDto tokenRefreshResponseDto = tokenService.refreshAccessTokenAndRefreshToken(tokenRefreshRequestDto);
        return new DataResponseDto<>(Code.OK, "토큰이 성공적으로 갱신되었습니다.", tokenRefreshResponseDto);
    }
}
