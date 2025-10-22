package com.ktb.community.strategy.post;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.enums.PostType;
import org.springframework.stereotype.Component;

@Component
public class NoticePostCreationStrategy implements PostCreationStrategy {
    @Override
    public Post createPost(Long userId, String title, String content, String postImageKey, PostType postType) {
        return null;
    }
}
