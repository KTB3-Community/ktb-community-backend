package com.ktb.community.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Comment {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public Comment(Long id, Long userId, Long postId, String content, int likeCount,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Comment withId(long newId) {
        return this.toBuilder()
                .id(newId)
                .build();
    }

    public static Comment createComment(Long userId, Long postId, String content) {
        LocalDateTime now = LocalDateTime.now();
        return Comment.builder()
                .userId(userId)
                .postId(postId)
                .content(content)
                .likeCount(0)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void updateComment(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseLikeCount() {
        this.likeCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
            this.updatedAt = LocalDateTime.now();
        }
    }
}
