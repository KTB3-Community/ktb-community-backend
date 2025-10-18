package com.ktb.community.repository;


import com.ktb.community.domain.CommentLike;
import com.ktb.community.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class InMemoryCommentLikeRepository implements CommentLikeRepository {

    private final IdGenerator idGenerator;
    private final Map<Long, CommentLike> storage = new ConcurrentHashMap<>();

    @Override
    public synchronized CommentLike save(CommentLike commentLike) {
        if (commentLike.getId() == null || commentLike.getId() == 0L) {
            commentLike = commentLike.withId(idGenerator.next("commentLike"));
        }
        storage.put(commentLike.getId(), commentLike);
        return commentLike;
    }

    @Override
    public CommentLike findByUserIdAndCommentId(Long userId, Long commentId) {
        return storage.values().stream()
                .filter(like -> like.getUserId().equals(userId)
                        && like.getCommentId().equals(commentId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Long commentId) {
        storage.remove(commentId);
    }
}
