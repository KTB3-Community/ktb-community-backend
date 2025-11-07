package com.ktb.community.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "post_likes")
@DiscriminatorValue("POST")
@PrimaryKeyJoinColumn(name = "likes_id")
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PostLikes extends Likes {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static PostLikes createPostLike(User user, Post post) {
        return PostLikes.builder()
                .user(user)
                .post(post)
                .build();
    }

//    public PostLikes withId(long newId) {
//        return this.toBuilder()
//                .id(newId)
//                .build();
//    }
}
