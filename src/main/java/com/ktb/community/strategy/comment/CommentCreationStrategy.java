package com.ktb.community.strategy.comment;


import com.ktb.community.domain.Comment;
import com.ktb.community.domain.enums.CommentType;

public interface CommentCreationStrategy {

    Comment createComment(Long userId, Long postId, String content, CommentType commentType);
}
