package com.ktb.community.mapper;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.CommentLike;
import com.ktb.community.domain.Post;
import com.ktb.community.domain.PostLike;
import com.ktb.community.dto.CreateCommentLikeResponseDto;
import com.ktb.community.dto.CreatePostLikeResponseDto;
import com.ktb.community.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

    public CreatePostLikeResponseDto mapToCreatePostLikeResponseDto(PostLike postLike, Post post) {
        return CreatePostLikeResponseDto.builder()
                .postLikeId(postLike.getId())
                .postId(post.getId())
                .createdAt(postLike.getCreatedAt())
                .updatedAt(postLike.getUpdatedAt())
                .build();
    }

    public CreateCommentLikeResponseDto mapToCreateCommentLikeResponseDto(CommentLike commentLike, Comment comment) {
        return CreateCommentLikeResponseDto.builder()
                .commentLikeId(comment.getId())
                .createdAt(commentLike.getCreatedAt())
                .updatedAt(commentLike.getUpdatedAt())
                .build();
    }
}
