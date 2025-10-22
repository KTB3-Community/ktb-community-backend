package com.ktb.community.mapper;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.dto.*;
import com.ktb.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserRepository userRepository;

    public CreatePostResponseDto mapToCreatePostResponseDto(Post post, UserInfoDto userInfoDto) {
        return CreatePostResponseDto.builder()
                .postId(post.getId())
                .writer(userInfoDto)
                .title(post.getTitle())
                .content(post.getContent())
                .postImageKey(post.getPostImageKey())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }


    public PostInfoDto mapToPostInfoDto(Post post) {

        User user = userRepository.findById(post.getUserId());

        return PostInfoDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postImageKey(post.getPostImageKey())
                .writer(UserInfoDto.builder()
                        .userId(user.getId())
                        .nickname(user.getNickname())
                        .profileImageKey(user.getProfileImageKey())
                        .build())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public GetPostListResponseDto mapToGetPostListResponseDto(List<Post> posts, String nextCursor, boolean hasNext) {
        List<PostInfoDto> postInfoDtoList = posts.stream()
                .map(this::mapToPostInfoDto)
                .toList();

        return GetPostListResponseDto.builder()
                .posts(postInfoDtoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    public UpdatePostImageResponseDto mapToUpdatePostImageResponseDto(Long postId, String postImageUrl, Post post) {
        return UpdatePostImageResponseDto.builder()
                .postId(postId)
                .postImageUrl(postImageUrl)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}

