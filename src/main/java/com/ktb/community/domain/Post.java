package com.ktb.community.domain;

import com.ktb.community.domain.enums.PostType;
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
    private PostType postType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public Post(Long id, Long userId, String title, String content, String postImageKey,
                int likeCount, int commentCount, int viewCount, PostType postType,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postImageKey = postImageKey;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.postType = postType;
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
                .postType(PostType.BASIC)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public Post updatePostImage(String postImageKey) {
        return toBuilder()
                .postImageKey(postImageKey)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Post deletePostImage() {
        return toBuilder()
                .postImageKey(null)
                .build();
    }

    public Post updatePost(String title, String content, String postImageKey) {
        return toBuilder()
                .title(title)
                .content(content)
                .postImageKey(postImageKey)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Post increaseLikeCount() {
        return toBuilder()
                .likeCount(likeCount+1)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Post decreaseLikeCount() {
        Post post = null;
        if (this.likeCount > 0) {
            post = toBuilder()
                    .likeCount(likeCount-1)
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
        return post;
    }
}
