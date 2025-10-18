package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetCommentListResponseDto {

    private List<CommentInfoDto> comments;
    private String nextCursor;
    private boolean hasNext;
}
