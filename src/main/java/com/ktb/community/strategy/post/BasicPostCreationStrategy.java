package com.ktb.community.strategy.post;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.enums.PostType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BasicPostCreationStrategy implements PostCreationStrategy{

    @Override
    public Post createPost(Long userId, String title, String content, String postImageKey, PostType postType) {
        return Post.createPost(userId, title, content, postImageKey, postType);
    }

}
