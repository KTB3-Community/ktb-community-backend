package com.ktb.community.service;

import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.dto.*;
import com.ktb.community.mapper.PostMapper;
import com.ktb.community.repository.PostRepository;
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
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public CreatePostResponseDto createPost(Long userId, PostRequestDto createPostRequestDto) {

        String title = createPostRequestDto.getTitle();
        String content = createPostRequestDto.getContent();
        String postImageKey = createPostRequestDto.getPostImageKey();

        Post post = postRepository.save(
                Post.createPost(userId, title, content, postImageKey)
        );

        User user = userRepository.findById(userId);
        UserInfoDto writerDto = UserInfoDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageKey(user.getProfileImageKey())
                .build();

        return CreatePostResponseDto.builder()
                .postId(post.getId())
                .writer(writerDto)
                .title(post.getTitle())
                .content(post.getContent())
                .postImageKey(post.getPostImageKey())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

    }

    public PostInfoDto getPost(Long postId) {

        Post post = postRepository.findById(postId);

        return postMapper.toPostInfo(post);
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

        return postMapper.toGetPostListResponse(
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
            post.updatePost(title, content, postImageKey);
        }

        postRepository.save(post);

        return postMapper.toPostInfo(post);

    }

    public UpdatePostImageResponseDto updatePostImage(Long postId, UploadImageRequestDto uploadImageRequestDto) {

        Post post = postRepository.findById(postId);

        if (post.getPostImageKey() != null) {
            imageService.deleteImage(post.getPostImageKey());
        }

        UploadImageResponseDto uploadImageResponseDto = imageService.generatePresignedUrl(uploadImageRequestDto);
        String postImageUrl = uploadImageResponseDto.getS3UploadUrl();

        post.updatePostImage(postImageUrl);

        postRepository.save(post);

        return UpdatePostImageResponseDto.builder()
                .postId(postId)
                .postImageUrl(postImageUrl)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public void deletePostImage(Long postId) {

        Post post = postRepository.findById(postId);

        if (post.getPostImageKey() != null) {
            imageService.deleteImage(post.getPostImageKey());
        }

        post.deletePostImage();
    }

    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }


}
