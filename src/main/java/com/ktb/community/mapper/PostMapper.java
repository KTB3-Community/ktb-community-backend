package com.ktb.community.mapper;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.dto.GetPostListResponseDto;
import com.ktb.community.dto.PostInfoDto;
import com.ktb.community.dto.UserInfoDto;
import com.ktb.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserRepository userRepository;

    public PostInfoDto toPostInfo(Post post) {

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

    public GetPostListResponseDto toGetPostListResponse(List<Post> posts, String nextCursor, boolean hasNext) {
        List<PostInfoDto> postDtos = posts.stream()
                .map(this::toPostInfo)
                .toList();

        return GetPostListResponseDto.builder()
                .posts(postDtos)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}

