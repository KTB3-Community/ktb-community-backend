package com.ktb.community.dto;

import com.ktb.community.domain.enums.PostType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequestDto {

    private String title;
    private String content;
    private String postImageKey;
    private PostType postType;
}
