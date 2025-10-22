package com.ktb.community.strategy.comment;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.enums.CommentType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BasicCommentCreationStrategy implements CommentCreationStrategy{
    @Override
    public Comment createComment(Long userId, Long postId, String content, CommentType commentType) {
        return Comment.createComment(userId, postId, content, commentType);
    }
}
