package com.ktb.community.strategy.post;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.PostType;

public interface PostCreationStrategy {

    PostType getPostType();
    Post createPost(User user, String title, String content, String postImageKey);
}
