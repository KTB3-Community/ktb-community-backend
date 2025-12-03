package com.ktb.community.mapper;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.User;
import com.ktb.community.dto.CommentInfoDto;
import com.ktb.community.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public CommentInfoDto mapToCommentInfoDto(Comment comment, User user) {
        return CommentInfoDto.builder()
                .commentId(comment.getId())
                .writer(UserInfoDto.builder()
                        .userId(user.getId())
                        .nickname(user.getNickname())
                        .profileImageKey(user.getProfileImageKey())
                        .build())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public List<CommentInfoDto> mapToCommentInfoDtoList(List<Comment> comments, Map<Long, User> userMap) {
        return comments.stream()
                .map(comment -> {
                    User user = userMap.get(comment.getUser().getId());

                    return CommentInfoDto.builder()
                            .commentId(comment.getId())
                            .writer(UserInfoDto.builder()
                                    .userId(user.getId())
                                    .nickname(user.getNickname())
                                    .profileImageKey(user.getProfileImageKey())
                                    .build())
                            .content(comment.getContent())
                            .likeCount(comment.getLikeCount())
                            .createdAt(comment.getCreatedAt())
                            .updatedAt(comment.getUpdatedAt())
                            .build();
                })
                .toList();
    }

}
