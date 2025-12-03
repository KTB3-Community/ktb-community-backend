package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.Post;
import com.ktb.community.domain.User;
import com.ktb.community.dto.*;
import com.ktb.community.mapper.PostMapper;
import com.ktb.community.repository.PostRepository;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.strategy.post.PostCreationStrategy;
import com.ktb.community.strategy.resolver.PostCreationStrategyResolver;
import com.ktb.community.util.cursor.CursorEncoder;
import com.ktb.community.util.user.UserAuthenticationUtils;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final PostCreationStrategyResolver postCreationStrategyResolver;
    private final UserAuthenticationUtils userAuthenticationUtils;


    public CreatePostResponseDto createPost(PostRequestDto postRequestDto) {

        User user = userAuthenticationUtils.getCurrentUser();
        PostCreationStrategy postCreationStrategy = postCreationStrategyResolver
                .getStrategy(postRequestDto.getPostType());

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String postImageKey = postRequestDto.getPostImageKey();

        Post post = postRepository.save(
                postCreationStrategy.createPost(user, title, content, postImageKey)
        );

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageKey(user.getProfileImageKey())
                .build();

        return postMapper.mapToCreatePostResponseDto(post, userInfoDto);

    }

    public PostInfoDto getPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));
        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new GeneralException(Code.USER_NOT_FOUND));

        return postMapper.mapToPostInfoDto(post, user);
    }

    public GetPostListResponseDto getPostList(LocalDateTime cursor, int limit) {

        List<Post> posts = postRepository.findAllByCreatedAt(cursor, limit);

        if (posts.isEmpty()) {
            return postMapper.mapToGetPostListResponseDto(
                    null,
                    Collections.emptyList(),
                    null,
                    false
                    );
        }

        List<Long> userIdList = posts.stream()
                .map(post -> post.getUser().getId())
                .distinct()
                .toList();

        Map<Long, User> userMap = userRepository.findAllById(userIdList)
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        LocalDateTime nextCursorTime = posts.getLast().getCreatedAt();
        boolean hasNext = postRepository.hasNext(nextCursorTime);

        return postMapper.mapToGetPostListResponseDto(
                userMap,
                posts,
                hasNext ? CursorEncoder.encode(nextCursorTime) : null,
                hasNext
        );

    }

    public PostInfoDto updatePost(Long postId, PostRequestDto postRequestDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));
        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new GeneralException(Code.USER_NOT_FOUND));

        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        String postImageKey = postRequestDto.getPostImageKey();

        if (title != null && content != null) {
            postRepository.save(post.updatePost(title, content, postImageKey));
        }

        return postMapper.mapToPostInfoDto(post, user);

    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));

        postRepository.delete(post);
    }

}
