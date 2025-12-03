package com.ktb.community.domain;

import jakarta.persistence.*;

import com.ktb.community.domain.enums.PostType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String postImageKey;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    private LocalDateTime deletedAt;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder(toBuilder = true)
    public Post(String title, String content, String postImageKey, int likeCount, int commentCount, int viewCount,
                PostType postType, LocalDateTime deletedAt, boolean isDeleted, User user) {
        this.title = title;
        this.content = content;
        this.postImageKey = postImageKey;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.postType = postType;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    public static Post createPost(User user, String title, String content, String postImageKey) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .postImageKey(postImageKey)
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .postType(PostType.BASIC)
                .deletedAt(null)
                .isDeleted(false)
                .build();
    }

    public Post updatePost(String title, String content, String postImageKey) {
        return toBuilder()
                .title(title)
                .content(content)
                .postImageKey(postImageKey)
                .build();
    }

    public Post increaseLikeCount() {
        return toBuilder()
                .likeCount(likeCount+1)
                .build();
    }

    public Post decreaseLikeCount() {
        int updatedCount = Math.max(0, this.likeCount - 1);
        return toBuilder()
                .likeCount(updatedCount)
                .build();
    }

}


//    public Post withId(long newId) {
//        return this.toBuilder()
//                .id(newId)
//                .build();
//    }
