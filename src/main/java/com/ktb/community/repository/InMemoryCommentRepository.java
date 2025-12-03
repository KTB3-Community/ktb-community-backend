package com.ktb.community.repository;

import com.ktb.community.domain.Comment;
import com.ktb.community.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
//
//@Component
//@RequiredArgsConstructor
//public class InMemoryCommentRepository implements CommentRepository {
//
//    private final IdGenerator idGenerator;
//    private final Map<Long, Comment> storage = new ConcurrentHashMap<>();
//
//    @Override
//    public synchronized Comment save(Comment comment) {
//        if (comment.getId() == null || comment.getId() == 0L) {
//            comment = comment.withId(idGenerator.next("comment"));
//        }
//        storage.put(comment.getId(), comment);
//        return comment;
//    }
//
//    @Override
//    public Comment findById(Long commentId) {
//        return storage.get(commentId);
//    }
//
//    @Override
//    public List<Comment> findAllByPostId(Long postId, LocalDateTime cursor, int limit) {
//        Stream<Comment> stream = storage.values().stream()
//                .filter(comment -> comment.getPostId().equals(postId))
//                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed()); // 최신순
//
//        if (cursor != null) {
//            stream = stream.filter(comment -> comment.getCreatedAt().isBefore(cursor));
//        }
//
//        return stream.limit(limit).toList();
//    }
//
//    @Override
//    public boolean hasNext(LocalDateTime nextTimeCursor) {
//        return storage.values().stream()
//                .anyMatch(comment -> comment.getCreatedAt().isBefore(nextTimeCursor));
//    }
//
//    @Override
//    public void delete(Long commentId) {
//        storage.remove(commentId);
//    }
//
//
//}
