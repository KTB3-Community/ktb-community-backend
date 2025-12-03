package com.ktb.community.repository;

import com.ktb.community.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    PostImage findByPostId (Long postId);
}
