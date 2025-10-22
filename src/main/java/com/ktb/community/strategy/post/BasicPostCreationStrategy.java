package com.ktb.community.strategy.post;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BasicPostCreationStrategy implements PostCreationStrategy{

    @Override
    public Post createPost(User user, String title, String content, String postImageKey) {
        return Post.createPost(user.getId(), title, content, postImageKey);
    }

}
