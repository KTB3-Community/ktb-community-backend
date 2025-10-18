package com.ktb.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class CommentLike extends Like{

    private Long commentId;

    public static CommentLike createCommentLike(Long userId, Long commentId) {
        LocalDateTime now = LocalDateTime.now();
        return CommentLike.builder()
                .userId(userId)
                .commentId(commentId)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public CommentLike withId(long newId) {
        return this.toBuilder()
                .id(newId)
                .build();
    }
}
