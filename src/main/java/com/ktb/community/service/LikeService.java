package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.*;
import com.ktb.community.dto.CreateCommentLikeResponseDto;
import com.ktb.community.dto.CreatePostLikeResponseDto;
import com.ktb.community.mapper.LikeMapper;
import com.ktb.community.repository.CommentLikeRepository;
import com.ktb.community.repository.CommentRepository;
import com.ktb.community.repository.PostLikesRepository;
import com.ktb.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final LikeMapper likeMapper;

    public CreatePostLikeResponseDto createPostLike(User user, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));

        PostLikes postLikes = postLikesRepository.save(PostLikes.createPostLike(user, post));
        postRepository.save(post.increaseLikeCount());

        return likeMapper.mapToCreatePostLikeResponseDto(postLikes, post);

    }

    public CreateCommentLikeResponseDto createCommentLike(User user, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(Code.COMMENT_NOT_FOUND));

        CommentLikes commentLike = commentLikeRepository.save(
                CommentLikes.createCommentLike(user, comment));

        commentRepository.save(comment.increaseLikeCount());

        return likeMapper.mapToCreateCommentLikeResponseDto(commentLike, comment);

    }

    public void deletePostLike(Long userId, Long postId) {

        PostLikes postLike = postLikesRepository.findByUserIdAndPostId(userId, postId);
        postLikesRepository.delete(postLike);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));
        postRepository.save(post.decreaseLikeCount());

    }

    public void deleteCommentLike(Long userId, Long commentId) {

        CommentLikes commentLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId);
        commentLikeRepository.delete(commentLike);

        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new GeneralException(Code.COMMENT_NOT_FOUND));
        commentRepository.save(comment.decreaseLikeCount());

    }
}
