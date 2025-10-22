package com.ktb.community.domain;

import com.ktb.community.domain.enums.CommentType;
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
    private CommentType commentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public Comment(Long id, Long userId, Long postId, String content, int likeCount, CommentType commentType,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.likeCount = likeCount;
        this.commentType = commentType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Comment withId(long newId) {
        return this.toBuilder()
                .id(newId)
                .build();
    }

    public static Comment createComment(Long userId, Long postId, String content, CommentType commentType) {
        LocalDateTime now = LocalDateTime.now();
        return Comment.builder()
                .userId(userId)
                .postId(postId)
                .content(content)
                .likeCount(0)
                .commentType(commentType)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public Comment updateComment(String content) {
        return toBuilder()
                .content(content)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Comment increaseLikeCount() {
        return toBuilder()
                .likeCount(likeCount+1)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Comment decreaseLikeCount() {
        Comment comment = null;
        if (this.likeCount > 0) {
            comment = toBuilder()
                    .likeCount(likeCount-1)
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
        return comment;
    }
}
