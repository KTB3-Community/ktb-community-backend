package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.*;
import com.ktb.community.service.PostService;
import com.ktb.community.service.TokenService;
import com.ktb.community.util.cursor.CursorEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TokenService tokenService;

    @PostMapping("/posts")
    public DataResponseDto<CreatePostResponseDto> createPost(@RequestHeader("Authorization") String authorizationHeader,
                                                             @RequestBody PostRequestDto createPostRequestDto) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long userId = tokenService.extractUserId(token);
        CreatePostResponseDto createPostResponseDto = postService.createPost(userId, createPostRequestDto);
        return new DataResponseDto<>(Code.OK, "게시물 생성이 성공적으로 완료되었습니다.", createPostResponseDto);
    }

    @GetMapping("/posts")
    public DataResponseDto<GetPostListResponseDto> getPostList(@RequestParam(required = false) String cursor,
                                                               @RequestParam(defaultValue = "10") int limit) {
        LocalDateTime decodedCursor = CursorEncoder.decode(cursor);
        GetPostListResponseDto getPostListResponseDto = postService.getPostList(decodedCursor, limit);
        return new DataResponseDto<>(Code.OK, "게시물 목록 조회가 성공적으로 완료되었습니다.", getPostListResponseDto);
    }

    @GetMapping("/posts/{postId}")
    public DataResponseDto<PostInfoDto> getPost(@PathVariable Long postId) {
        PostInfoDto getPostResponseDto = postService.getPost(postId);
        return new DataResponseDto<>(Code.OK, "게시물 조회가 성공적으로 완료되었습니다.", getPostResponseDto);
    }

    @PatchMapping("/posts/{postId}")
    public DataResponseDto<PostInfoDto> updatePost(@PathVariable Long postId,
                                                         @RequestBody PostRequestDto postRequestDto) {
        PostInfoDto postInfoDto = postService.updatePost(postId, postRequestDto);
        return new DataResponseDto<>(Code.OK, "게시물 수정이 성공적으로 완료되었습니다.", postInfoDto);
    }

    @PutMapping("/posts/{postId}/image")
    public DataResponseDto<UpdatePostImageResponseDto> updatePostImage(@PathVariable Long postId,
                                                                       @RequestBody UploadImageRequestDto uploadImageRequestDto) {
        UpdatePostImageResponseDto updatePostImageResponseDto = postService.updatePostImage(postId, uploadImageRequestDto);
        return new DataResponseDto<>(Code.OK, "게시물 이미지 변경이 성공적으로 완료되었습니다.", updatePostImageResponseDto);
    }

    @DeleteMapping("/posts/{postId}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostImage(@PathVariable Long postId) {
        postService.deletePostImage(postId);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
