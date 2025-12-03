package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.LoginRequestDto;
import com.ktb.community.dto.LoginResponseDto;
import com.ktb.community.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    @PostMapping("/sessions")
    public DataResponseDto<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                   HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto, response);
        return new DataResponseDto<>(Code.OK, "로그인이 성공적으로 완료되었습니다.", loginResponseDto);
    }

    @DeleteMapping("/sessions/current")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        authService.logout();
    }

}
