package com.ktb.community.repository;

import com.ktb.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = """
            SELECT *
            FROM post
            WHERE (:cursor IS NULL OR created_at < :cursor)
            ORDER BY created_at DESC
            LIMIT :limit
            """,
            nativeQuery = true
    )
    List<Post> findAllByCreatedAt(
            @Param("cursor") LocalDateTime cursor,
            @Param("limit") int limit
    );

    @Query("""
    SELECT COUNT(p) > 0
    FROM Post p
    WHERE p.createdAt < :cursor
    """)
    boolean hasNext(@Param("cursor") LocalDateTime cursor);

}
