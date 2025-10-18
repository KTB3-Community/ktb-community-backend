package com.ktb.community.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequestDto {

    private String email;
    private String nickname;
    private String password;
    private String profileImageKey;
}
