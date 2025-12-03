package com.ktb.community.dto;

import com.ktb.community.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

    private Long userId;
    private String nickname;
    private String profileImageKey;


    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageKey(user.getProfileImageKey())
                .build();
    }
}
