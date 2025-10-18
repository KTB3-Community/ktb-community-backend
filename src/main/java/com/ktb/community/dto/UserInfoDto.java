package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

    private Long userId;
    private String nickname;
    private String profileImageKey;
}
