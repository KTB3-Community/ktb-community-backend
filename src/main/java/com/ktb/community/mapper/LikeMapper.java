package com.ktb.community.mapper;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.CommentLikes;
import com.ktb.community.domain.Post;
import com.ktb.community.domain.PostLikes;
import com.ktb.community.dto.CreateCommentLikeResponseDto;
import com.ktb.community.dto.CreatePostLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

    public CreatePostLikeResponseDto mapToCreatePostLikeResponseDto(PostLikes postLikes, Post post) {
        return CreatePostLikeResponseDto.builder()
                .postLikeId(postLikes.getId())
                .postId(post.getId())
                .createdAt(postLikes.getCreatedAt())
                .updatedAt(postLikes.getUpdatedAt())
                .build();
    }

    public CreateCommentLikeResponseDto mapToCreateCommentLikeResponseDto(CommentLikes commentLikes, Comment comment) {
        return CreateCommentLikeResponseDto.builder()
                .commentLikeId(comment.getId())
                .createdAt(commentLikes.getCreatedAt())
                .updatedAt(commentLikes.getUpdatedAt())
                .build();
    }
}
