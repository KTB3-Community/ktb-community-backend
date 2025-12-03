package com.ktb.community.mapper;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.PostImage;
import com.ktb.community.domain.User;
import com.ktb.community.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostMapper {

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


    public PostInfoDto mapToPostInfoDto(Post post, User user) {
        return PostInfoDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postImageKey(post.getPostImageKey())
                .writer(UserInfoDto.from(user))
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public GetPostListResponseDto mapToGetPostListResponseDto(Map<Long, User> userMap, List<Post> posts, String nextCursor, boolean hasNext) {
        List<PostInfoDto> postInfoDtoList = posts.stream()
                .map(post -> {
                    User user = userMap.get(post.getUser().getId());
                    return mapToPostInfoDto(post, user);
                })
                .toList();

        return GetPostListResponseDto.builder()
                .posts(postInfoDtoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    public UpdatePostImageResponseDto mapToUpdatePostImageResponseDto(Long postId, String postImageUrl, PostImage postImage) {
        return UpdatePostImageResponseDto.builder()
                .postId(postId)
                .postImageUrl(postImageUrl)
                .createdAt(postImage.getCreatedAt())
                .updatedAt(postImage.getUpdatedAt())
                .build();
    }
}

