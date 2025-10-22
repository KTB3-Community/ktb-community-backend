package com.ktb.community.strategy.post;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.PostType;
import com.ktb.community.domain.enums.Role;
import org.springframework.stereotype.Component;

@Component("noticePostCreationStrategy")
public class NoticePostCreationStrategy implements PostCreationStrategy {

    @Override
    public Post createPost(User user, String title, String content, String postImageKey) {
        if (user.getRole() != Role.ADMIN) {
            throw new GeneralException(Code.FORBIDDEN);
        }

        return Post.createPost(user.getId(), title, content, postImageKey)
                .toBuilder()
                .postType(PostType.NOTICE)
                .build();
    }
}

