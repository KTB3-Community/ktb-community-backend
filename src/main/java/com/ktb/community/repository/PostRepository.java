package com.ktb.community.repository;

import com.ktb.community.domain.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository {

    Post save(Post post);
    Post findById(Long postId);
    List<Post> findAllByCreatedAt(LocalDateTime createdAt, int limit);
    boolean hasNext(LocalDateTime nextCursorTime);
    void delete(Long postId);
}
