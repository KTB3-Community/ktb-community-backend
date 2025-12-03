package com.ktb.community.repository;

import com.ktb.community.domain.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long> {

    CommentLikes findByUserIdAndCommentId(Long userId, Long postId);
}
