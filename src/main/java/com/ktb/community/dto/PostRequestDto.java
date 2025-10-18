package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequestDto {

    private String title;
    private String content;
    private String postImageKey;
}
