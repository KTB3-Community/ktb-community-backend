package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.CreateCommentLikeResponseDto;
import com.ktb.community.dto.CreatePostLikeResponseDto;
import com.ktb.community.service.LikeService;
import com.ktb.community.util.authorization.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final Authorization authorization;
    private final LikeService likeService;

    @PostMapping("/posts/{postId}/likes")
    public DataResponseDto<CreatePostLikeResponseDto> createPostLike(@RequestHeader("Authorization") String authorizationHeader,
                                                                     @PathVariable Long postId) {
        CreatePostLikeResponseDto createPostLikeResponseDto = likeService.createPostLike(authorization.extractUserIdFromHeader(authorizationHeader), postId);
        return new DataResponseDto<>(Code.OK, "게시물 좋아요 처리가 성공적으로 완료되었습니다.", createPostLikeResponseDto);
    }

    @PostMapping("/comments/{commentId}/likes")
    public DataResponseDto<CreateCommentLikeResponseDto> createCommentLike(@RequestHeader("Authorization") String authorizationHeader,
                                                                           @PathVariable Long commentId) {
        CreateCommentLikeResponseDto createCommentLikeResponseDto = likeService.createCommentLike(authorization.extractUserIdFromHeader(authorizationHeader), commentId);
        return new DataResponseDto<>(Code.OK, "댓글 좋아요 처리가 성공적으로 완료되었습니다.", createCommentLikeResponseDto);
    }

    @DeleteMapping("/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostLike(@RequestHeader("Authorization") String authorizationHeader,
                               @PathVariable Long postId) {
        likeService.deletePostLike(authorization.extractUserIdFromHeader(authorizationHeader), postId);
    }

    @DeleteMapping("/comments/{commentId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentLike(@RequestHeader("Authorization") String authorizationHeader,
                                  @PathVariable Long commentId) {
        likeService.deleteCommentLike(authorization.extractUserIdFromHeader(authorizationHeader), commentId);
    }
}
