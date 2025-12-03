package com.ktb.community.repository;

import com.ktb.community.domain.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    PostLikes findByUserIdAndPostId(Long userId, Long postId);
}
