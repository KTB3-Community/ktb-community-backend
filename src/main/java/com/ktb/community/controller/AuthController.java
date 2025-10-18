package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.CreateUserRequestDto;
import com.ktb.community.dto.CreateUserResponseDto;
import com.ktb.community.dto.LoginRequestDto;
import com.ktb.community.dto.LoginResponseDto;
import com.ktb.community.service.TokenService;
import com.ktb.community.service.UserService;
import com.ktb.community.service.AuthService;
import com.ktb.community.util.authorization.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;
    private final Authorization authorization;

    @PostMapping("/sessions")
    public DataResponseDto<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return new DataResponseDto<>(Code.OK, "로그인이 성공적으로 완료되었습니다.", loginResponseDto);
    }

    @DeleteMapping("/sessions/current")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        authService.logout(authorization.extractUserInfoFromToken(authorizationHeader));
    }

}
