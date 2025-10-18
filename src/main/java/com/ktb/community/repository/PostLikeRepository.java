package com.ktb.community.repository;

import com.ktb.community.domain.PostLike;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository {

    PostLike save(PostLike postLike);
    PostLike findByUserIdAndPostId(Long userId, Long postId);
    void delete(Long postId);
}
