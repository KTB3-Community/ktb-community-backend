package com.ktb.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_likes")
@DiscriminatorValue("COMMENT")
@PrimaryKeyJoinColumn(name = "likes_id")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class CommentLikes extends Likes {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static CommentLikes createCommentLike(User user, Comment comment) {
        return CommentLikes.builder()
                .user(user)
                .comment(comment)
                .build();
    }

//    public CommentLike withId(long newId) {
//        return this.toBuilder()
//                .id(newId)
//                .build();
//    }
}
