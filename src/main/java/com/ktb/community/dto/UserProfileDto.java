package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {

    private Long userId;
    private String email;
    private String nickname;
    private String presignedProfileUrl;
}
