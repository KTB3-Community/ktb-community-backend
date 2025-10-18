package com.ktb.community.service;

import com.ktb.community.domain.*;
import com.ktb.community.dto.CreateCommentLikeResponseDto;
import com.ktb.community.dto.CreatePostLikeResponseDto;
import com.ktb.community.repository.CommentLikeRepository;
import com.ktb.community.repository.CommentRepository;
import com.ktb.community.repository.PostLikeRepository;
import com.ktb.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CreatePostLikeResponseDto createPostLike(Long userId, Long postId) {

        PostLike postLike = postLikeRepository.save(
                PostLike.createPostLike(userId, postId));

        Post post = postRepository.findById(postId);
        post.increaseLikeCount();

        return CreatePostLikeResponseDto.builder()
                .postLikeId(postLike.getId())
                .createdAt(postLike.getCreatedAt())
                .updatedAt(postLike.getUpdatedAt())
                .build();

    }

    public CreateCommentLikeResponseDto createCommentLike(Long userId,Long commentId) {

        Comment comment = commentRepository.findById(commentId);

        CommentLike commentLike = commentLikeRepository.save(
                CommentLike.createCommentLike(userId, commentId));

        comment.increaseLikeCount();

        return CreateCommentLikeResponseDto.builder()
                .commentLikeId(comment.getId())
                .createdAt(commentLike.getCreatedAt())
                .updatedAt(commentLike.getUpdatedAt())
                .build();

    }

    public void deletePostLike(Long userId, Long postId) {

        PostLike postLike = postLikeRepository.findByUserIdAndPostId(userId, postId);
        postLikeRepository.delete(postLike.getId());

        Post post = postRepository.findById(postId);
        post.decreaseLikeCount();

    }

    public void deleteCommentLike(Long userId, Long commentId) {

        CommentLike commentLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId);
        postLikeRepository.delete(commentLike.getId());

        Comment comment = commentRepository.findById(commentId);
        comment.decreaseLikeCount();

    }
}
