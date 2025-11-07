package com.ktb.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder(toBuilder = true)
    public Comment(Long id, String content, int likeCount, User user, Post post) {
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.user = user;
        this.post = post;
    }

    public static Comment createComment(String content, Post post, User user) {
        return Comment.builder()
                .content(content)
                .likeCount(0)
                .post(post)
                .user(user)
                .build();
    }

    public Comment updateComment(String content) {
        return toBuilder()
                .content(content)
                .build();
    }

    public Comment increaseLikeCount() {
        return toBuilder()
                .likeCount(likeCount+1)
                .build();
    }

    public Comment decreaseLikeCount() {
        return toBuilder()
                .likeCount(Math.max(0, this.likeCount - 1))
                .build();
    }


}

//    public Comment withId(long newId) {
//        return this.toBuilder()
//                .id(newId)
//                .build();
//    }
