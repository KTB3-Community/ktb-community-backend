package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetPostListResponseDto {

    private List<PostInfoDto> posts;
    private String nextCursor;
    private boolean hasNext;
}
