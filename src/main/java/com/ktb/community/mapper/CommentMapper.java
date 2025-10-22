package com.ktb.community.mapper;

import com.ktb.community.domain.Comment;
import com.ktb.community.domain.User;
import com.ktb.community.dto.CommentInfoDto;
import com.ktb.community.dto.UserInfoDto;
import com.ktb.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserRepository userRepository;

    public List<CommentInfoDto> mapToCommentInfoDtoList(List<Comment> comments) {
        return comments.stream()
                .map(comment -> {
                    User user = userRepository.findById(comment.getUserId());

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
