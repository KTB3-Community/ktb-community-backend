package com.ktb.community.strategy.post;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;

public interface PostCreationStrategy {

    Post createPost(User user, String title, String content, String postImageKey);
}
