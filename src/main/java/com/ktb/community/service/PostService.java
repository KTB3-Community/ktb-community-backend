package com.ktb.community.service;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.PostType;
import com.ktb.community.domain.enums.Role;
import com.ktb.community.dto.*;
import com.ktb.community.mapper.PostMapper;
import com.ktb.community.repository.PostRepository;
import com.ktb.community.strategy.post.PostCreationStrategy;
import com.ktb.community.util.cursor.CursorEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostMapper postMapper;
    private final Map<String, PostCreationStrategy> postCreationStrategies;

    public CreatePostResponseDto createPost(User user, PostRequestDto postRequestDto) {

        PostCreationStrategy postCreationStrategy = postCreationStrategies.get(determineStrategy(user, postRequestDto.getPostType()));

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String postImageKey = postRequestDto.getPostImageKey();

        Long userId = user.getId();

        Post post = postRepository.save(
                postCreationStrategy.createPost(userId, title, content, postImageKey, PostType.BASIC)
        );

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageKey(user.getProfileImageKey())
                .build();

        return postMapper.mapToCreatePostResponseDto(post, userInfoDto);

    }

    public PostInfoDto getPost(Long postId) {

        Post post = postRepository.findById(postId);

        return postMapper.mapToPostInfoDto(post);
    }

    public GetPostListResponseDto getPostList(LocalDateTime cursor, int limit) {

        List<Post> posts = postRepository.findAllByCreatedAt(cursor, limit);

        if (posts.isEmpty()) {
            return GetPostListResponseDto.builder()
                    .posts(Collections.emptyList())
                    .hasNext(false)
                    .build();
        }

        LocalDateTime nextCursorTime = posts.getLast().getCreatedAt();
        boolean hasNext = postRepository.hasNext(nextCursorTime);

        return postMapper.mapToGetPostListResponseDto(
                posts,
                hasNext ? CursorEncoder.encode(nextCursorTime) : null,
                hasNext
        );

    }

    public PostInfoDto updatePost(Long postId, PostRequestDto postRequestDto) {

        Post post = postRepository.findById(postId);

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String postImageKey = postRequestDto.getPostImageKey();

        if (title != null && content != null) {
            postRepository.save(post.updatePost(title, content, postImageKey));
        }

        return postMapper.mapToPostInfoDto(post);

    }

    public UpdatePostImageResponseDto updatePostImage(Long postId, UploadImageRequestDto uploadImageRequestDto) {

        Post post = postRepository.findById(postId);

        if (post.getPostImageKey() != null) {
            imageService.deleteImage(post.getPostImageKey());
        }

        UploadImageResponseDto uploadImageResponseDto = imageService.generatePresignedUrl(uploadImageRequestDto);
        String postImageUrl = uploadImageResponseDto.getS3UploadUrl();

        postRepository.save(post.updatePostImage(postImageUrl));

        return postMapper.mapToUpdatePostImageResponseDto(postId, postImageUrl, post);
    }

    public void deletePostImage(Long postId) {

        Post post = postRepository.findById(postId);

        if (post.getPostImageKey() != null) {
            imageService.deleteImage(post.getPostImageKey());
        }

        postRepository.save(post.deletePostImage());
    }

    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }

    private String determineStrategy(User user, PostType postType) {
        if (user.getRole().equals(Role.ADMIN) && postType.equals(PostType.NOTICE)) return "noticePostCreationStrategy";
        return "basicPostCreationStrategy";
    }

}
