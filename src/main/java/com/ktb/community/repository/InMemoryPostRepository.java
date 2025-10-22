package com.ktb.community.repository;

import com.ktb.community.domain.Post;
import com.ktb.community.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class InMemoryPostRepository implements PostRepository{

    private final IdGenerator idGenerator;
    private final Map<Long, Post> storage = new ConcurrentHashMap<>();


    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0L) {
            post = post.withId(idGenerator.next("post"));
        }
        storage.put(post.getId(), post);
        return post;
    }

    @Override
    public Post findById(Long postId) {
        return storage.get(postId);
    }

    @Override
    public List<Post> findAllByCreatedAt(LocalDateTime cursor, int limit) {
        return storage.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed()) // 최신순 정렬
                .filter(post -> cursor == null || post.getCreatedAt().isBefore(cursor))
                .limit(limit)
                .toList();
    }

    public boolean hasNext(LocalDateTime nextTimeCursor) {
        return storage.values().stream()
                .anyMatch(post -> post.getCreatedAt().isBefore(nextTimeCursor));
    }

    @Override
    public void delete(Long postId) {
        storage.remove(postId);
    }
}
