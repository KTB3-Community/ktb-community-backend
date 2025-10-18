package com.ktb.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PostLike extends Like {

    private Long postId;

    public static PostLike createPostLike(Long userId, Long postId) {
        LocalDateTime now = LocalDateTime.now();
        return PostLike.builder()
                .userId(userId)
                .postId(postId)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public PostLike withId(long newId) {
        return this.toBuilder()
                .id(newId)
                .build();
    }
}
