package com.ktb.community.repository;

//import com.ktb.community.domain.PostLike;
//import com.ktb.community.util.id.IdGenerator;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//@RequiredArgsConstructor
//public class InMemoryPostLikeRepository implements PostLikeRepository{
//
//    private final IdGenerator idGenerator;
//    private final Map<Long, PostLike> storage = new ConcurrentHashMap<>();
//
//    @Override
//    public synchronized PostLike save(PostLike postLike) {
//        if (postLike.getId() == null || postLike.getId() == 0L) {
//            postLike = postLike.withId(idGenerator.next("postLike"));
//        }
//        storage.put(postLike.getId(), postLike);
//        return postLike;
//    }
//
//    @Override
//    public PostLike findByUserIdAndPostId(Long userId, Long postId) {
//        return storage.values().stream()
//                .filter(like -> like.getUserId().equals(userId)
//                        && like.getPostId().equals(postId))
//                .findFirst()
//                .orElse(null);
//    }
//
//
//    @Override
//    public void delete(Long postLikeId) {
//        storage.remove(postLikeId);
//    }
//
//}
