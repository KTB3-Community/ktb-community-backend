package com.ktb.community.strategy.comment;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.enums.CommentType;
import org.springframework.stereotype.Component;

@Component
public class ReplyCommentCreationStrategy implements CommentCreationStrategy{
    @Override
    public Comment createComment(Long userId, Long postId, String content, CommentType commentType) {
        return null;
    }
}
