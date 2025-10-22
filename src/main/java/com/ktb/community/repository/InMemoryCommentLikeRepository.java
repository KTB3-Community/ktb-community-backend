package com.ktb.community.repository;


import com.ktb.community.domain.CommentLike;
import com.ktb.community.domain.CommentLikeKey;
import com.ktb.community.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class InMemoryCommentLikeRepository implements CommentLikeRepository {

    private final IdGenerator idGenerator;

    private final Map<Long, CommentLike> commentLikeStorage = new ConcurrentHashMap<>();
    private final Map<CommentLikeKey, CommentLike> commentLikeKeyStorage = new ConcurrentHashMap<>();

    @Override
    public synchronized CommentLike save(CommentLike commentLike) {
        if (commentLike.getId() == null || commentLike.getId() == 0L) {
            commentLike = commentLike.withId(idGenerator.next("commentLike"));
        }

        CommentLikeKey commentLikeKey = new CommentLikeKey(commentLike.getUserId(), commentLike.getCommentId());
        commentLikeStorage.put(commentLike.getId(), commentLike);
        commentLikeKeyStorage.put(commentLikeKey, commentLike);
        return commentLike;
    }

    @Override
    public CommentLike findByUserIdAndCommentId(Long userId, Long commentId) {
        return commentLikeKeyStorage.get(new CommentLikeKey(userId, commentId));
    }

    @Override
    public void delete(Long userId, Long commentId) {
        CommentLikeKey commentLikeKey = new CommentLikeKey(userId, commentId);
        CommentLike commentLike = commentLikeKeyStorage.remove(commentLikeKey);

        if (commentLike != null) {
            commentLikeStorage.remove(commentLike.getId());
        }
    }
}
