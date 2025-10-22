package com.ktb.community.strategy.post;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.enums.PostType;

public interface PostCreationStrategy {

    Post createPost(Long userId, String title, String content, String postImageKey, PostType postType);
}
