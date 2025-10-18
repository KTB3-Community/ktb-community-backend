package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.*;
import com.ktb.community.service.UserService;
import com.ktb.community.util.authorization.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    private final Authorization authorization;

    @PostMapping("/users")
    public DataResponseDto<CreateUserResponseDto> signup(@RequestBody CreateUserRequestDto createUserRequestBody) {
        CreateUserResponseDto userResponseDto = userService.createUser(createUserRequestBody);
        return new DataResponseDto<>(Code.OK, "회원가입이 성공적으로 완료되었습니다.", userResponseDto);
    }

    @PatchMapping("/users/{userId}")
    public DataResponseDto<UpdateUserInfoResponseDto> updateUserInfo(@PathVariable Long userId,
                                                                     @RequestBody UpdateUserInfoRequestDto updateUserInfoRequestDto) {
        UpdateUserInfoResponseDto updateUserInfoResponseDto = userService.updateUserInfo(userId, updateUserInfoRequestDto);
        return new DataResponseDto<>(Code.OK, "회원 정보 수정이 성공적으로 완료되었습니다.", updateUserInfoResponseDto);
    }

    @PatchMapping("/users/{userId}/password")
    public DataResponseDto<UpdateUserInfoResponseDto> updateUserPassword(@PathVariable Long userId,
                                                                         @RequestBody UpdateUserPasswordRequestDto updateUserPasswordRequestDto) {
        UpdateUserInfoResponseDto updateUserInfoResponseDto = userService.updateUserPassword(userId, updateUserPasswordRequestDto);
        return new DataResponseDto<>(Code.OK, "회원 정보 수정이 성공적으로 완료되었습니다.", updateUserInfoResponseDto);
    }

    @PutMapping("/users/{userId}/image")
    public DataResponseDto<UpdateUserImageResponseDto> updateUserImage(@PathVariable Long userId,
                                                                       @RequestBody UploadImageRequestDto uploadImageRequestDto) {
        UpdateUserImageResponseDto updateUserImageResponseDto = userService.updateUserImage(userId, uploadImageRequestDto);
        return new DataResponseDto<>(Code.OK, "회원 프로필 이미지 변경이 성공적으로 완료되었습니다.", updateUserImageResponseDto);
    }

    @DeleteMapping("/users/{userId}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserImage(@PathVariable Long userId) {
        userService.deleteUserImage(userId);
    }

    @DeleteMapping("/users/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader("Authorization") String authorizationHeader) {
        userService.deleteUser(authorization.extractUserInfoFromToken(authorizationHeader));
    }
}
