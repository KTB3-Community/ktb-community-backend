package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.*;
import com.ktb.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping("/users")
    public DataResponseDto<CreateUserResponseDto> signup(@RequestBody CreateUserRequestDto createUserRequestBody) {
        CreateUserResponseDto userResponseDto = userService.createUser(createUserRequestBody);
        return new DataResponseDto<>(Code.OK, "회원가입이 성공적으로 완료되었습니다.", userResponseDto);
    }

    @GetMapping("/users")
    public DataResponseDto<UserProfileDto> updateUserInfo() {
        UserProfileDto userProfileDto = userService.getUserProfileInfo();
        return new DataResponseDto<>(Code.OK, "회원 프로필 정보 조회가 성공적으로 완료되었습니다.", userProfileDto);
    }

    @PatchMapping("/users")
    public DataResponseDto<UpdateUserInfoResponseDto> updateUserInfo(@RequestBody UpdateUserInfoRequestDto updateUserInfoRequestDto) {
        UpdateUserInfoResponseDto updateUserInfoResponseDto = userService.updateUserInfo(updateUserInfoRequestDto);
        return new DataResponseDto<>(Code.OK, "회원 프로필 정보 수정이 성공적으로 완료되었습니다.", updateUserInfoResponseDto);
    }

    @DeleteMapping("/users/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        userService.deleteUser();
    }
}
