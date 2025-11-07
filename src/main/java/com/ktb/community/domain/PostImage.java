package com.ktb.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String postImageKey;
    private int orderIndex;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @Builder(toBuilder = true)
    protected PostImage(String postImageKey, int orderIndex, LocalDateTime createdAt, LocalDateTime deletedAt,
                     boolean isDeleted, Post post) {
        this.postImageKey = postImageKey;
        this.orderIndex = orderIndex;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.post = post;
    }

    public static PostImage createPostImage(Post post, String postImageKey, int orderIndex) {
        return PostImage.builder()
                .post(post)
                .postImageKey(postImageKey)
                .orderIndex(orderIndex)
                .deletedAt(null)
                .isDeleted(false)
                .build();
    }


    public PostImage updatePostImage(String postImageKey) {
        return toBuilder()
                .postImageKey(postImageKey)
                .build();
    }

    public PostImage deletePostImage() {
        return toBuilder()
                .postImageKey(null)
                .deletedAt(LocalDateTime.now())
                .isDeleted(true)
                .build();
    }
}


