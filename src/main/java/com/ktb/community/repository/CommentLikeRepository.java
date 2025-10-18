package com.ktb.community.repository;

import com.ktb.community.domain.CommentLike;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository {

    CommentLike save(CommentLike commentLike);
    CommentLike findByUserIdAndCommentId(Long userId, Long postId);
    void delete(Long commentLikeId);
}
