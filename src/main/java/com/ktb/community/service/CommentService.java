package com.ktb.community.service;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.User;
import com.ktb.community.dto.CommentInfoDto;
import com.ktb.community.dto.CommentRequestDto;
import com.ktb.community.dto.GetCommentListResponseDto;
import com.ktb.community.dto.UserInfoDto;
import com.ktb.community.mapper.CommentMapper;
import com.ktb.community.repository.CommentRepository;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.util.cursor.CursorEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;


    public CommentInfoDto createComment(User user, Long postId, CommentRequestDto commentRequestDto) {

        Comment comment = commentRepository.save(
                Comment.createComment(user.getId(), postId, commentRequestDto.getContent())
        );

        return commentMapper.mapToCommentInfoDto(comment, user);
    }

    public GetCommentListResponseDto getCommentList (Long postId, LocalDateTime cursor, int limit) {

        List<Comment> comments = commentRepository.findAllByPostId(postId, cursor, limit);

        if (comments.isEmpty()) {
            return GetCommentListResponseDto.builder()
                    .comments(Collections.emptyList())
                    .hasNext(false)
                    .build();
        }

        LocalDateTime nextCursorTime = comments.getLast().getCreatedAt();
        boolean hasNext = commentRepository.hasNext(nextCursorTime);

        return GetCommentListResponseDto.builder()
                .comments(commentMapper.mapToCommentInfoDtoList(comments))
                .nextCursor(hasNext ? CursorEncoder.encode(nextCursorTime) : null)
                .hasNext(hasNext)
                .build();

    }

    public CommentInfoDto updateComment(Long userId, Long commentId, CommentRequestDto commentRequestDto) {

        User user = userRepository.findById(userId);
        Comment comment = commentRepository.findById(commentId);

        commentRepository.save(comment.updateComment(commentRequestDto.getContent()));

        return CommentInfoDto.builder()
                .commentId(commentId)
                .writer(UserInfoDto.builder()
                        .userId(userId)
                        .nickname(user.getNickname())
                        .profileImageKey(user.getProfileImageKey())
                        .build())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId);
        commentRepository.delete(comment.getId());

    }

}
