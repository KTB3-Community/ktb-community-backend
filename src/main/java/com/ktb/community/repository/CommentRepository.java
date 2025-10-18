package com.ktb.community.repository;

import com.ktb.community.domain.Comment;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository {

    Comment save(Comment comment);
    Comment findById(Long commentId);
    List<Comment> findAllByPostId(Long postId, LocalDateTime cursor, int limit);
    boolean hasNext(LocalDateTime nextCursorTime);
    void delete(Long commentId);
}
