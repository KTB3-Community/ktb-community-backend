package com.ktb.community.dto;

import com.ktb.community.domain.enums.CommentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {

    private String content;
    private CommentType commentType;
}
