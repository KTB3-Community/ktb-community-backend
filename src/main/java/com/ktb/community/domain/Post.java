package com.ktb.community.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String postImageKey;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public Post(Long id, Long userId, String title, String content, String postImageKey, int likeCount, int commentCount, int viewCount,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postImageKey = postImageKey;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Post withId(long newId) {
        return this.toBuilder()
                .id(newId)
                .build();
    }

    public static Post createPost(Long userId, String title, String content, String postImageKey) {
        LocalDateTime now = LocalDateTime.now();
        return Post.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .postImageKey(postImageKey)
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void updatePostImage(String postImageKey) {
        this.postImageKey = postImageKey;
        this.updatedAt = LocalDateTime.now();
    }

    public void deletePostImage() {
        this.postImageKey = null;
    }

    public void updatePost(String title, String content, String postImageKey) {
        this.title = title;
        this.content = content;
        this.postImageKey = postImageKey;
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
