package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.Comment;
import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.dto.CommentInfoDto;
import com.ktb.community.dto.CommentRequestDto;
import com.ktb.community.dto.GetCommentListResponseDto;
import com.ktb.community.dto.UserInfoDto;
import com.ktb.community.mapper.CommentMapper;
import com.ktb.community.repository.CommentRepository;
import com.ktb.community.repository.PostRepository;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.util.cursor.CursorEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;


    public CommentInfoDto createComment(User user, Long postId, CommentRequestDto commentRequestDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));

        Comment comment = commentRepository.save(
                Comment.createComment(commentRequestDto.getContent(), post, user)
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

        // postService의 getPostList랑 중복 로직
        List<Long> userIdList = comments.stream()
                .map(comment -> comment.getUser().getId())
                .distinct()
                .toList();

        Map<Long, User> userMap = userRepository.findAllById(userIdList)
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        LocalDateTime nextCursorTime = comments.getLast().getCreatedAt();
        boolean hasNext = commentRepository.hasNext(nextCursorTime);

        return GetCommentListResponseDto.builder()
                .comments(commentMapper.mapToCommentInfoDtoList(comments, userMap))
                .nextCursor(hasNext ? CursorEncoder.encode(nextCursorTime) : null)
                .hasNext(hasNext)
                .build();

    }

    public CommentInfoDto updateComment(Long userId, Long commentId, CommentRequestDto commentRequestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(Code.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(Code.COMMENT_NOT_FOUND));

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

        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new GeneralException(Code.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);

    }

}
