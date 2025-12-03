package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserInfoRequestDto {
    private String nickname;
    private String profileImageKey;
    private String password;
}