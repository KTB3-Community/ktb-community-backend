package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.CommentInfoDto;
import com.ktb.community.dto.CommentRequestDto;
import com.ktb.community.dto.GetCommentListResponseDto;
import com.ktb.community.service.CommentService;
import com.ktb.community.util.authorization.Authorization;
import com.ktb.community.util.cursor.CursorEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final Authorization authorization;

    @PostMapping("/posts/{postId}/comments")
    public DataResponseDto<CommentInfoDto> createComment(@RequestHeader("Authorization") String authorizationHeader,
                                                         @PathVariable Long postId,
                                                         @RequestBody CommentRequestDto commentRequestDto) {
        CommentInfoDto commentInfoDto = commentService.createComment(authorization.extractUserFromHeader(authorizationHeader), postId, commentRequestDto);
        return new DataResponseDto<>(Code.OK, "댓글 생성이 성공적으로 완료되었습니다.", commentInfoDto);
    }

    @GetMapping("/posts/{postId}/comments")
    public DataResponseDto<GetCommentListResponseDto> getCommentList(@PathVariable Long postId,
                                                                     @RequestParam(required = false) String cursor,
                                                                     @RequestParam(defaultValue = "10") int limit) {
        LocalDateTime cursorTime = CursorEncoder.decode(cursor);
        GetCommentListResponseDto getCommentListResponseDto = commentService.getCommentList(postId, cursorTime, limit);
        return new DataResponseDto<>(Code.OK, "댓글 목록 조회가 성공적으로 완료되었습니다.", getCommentListResponseDto);
    }

    @PutMapping("/comments/{commentId}")
    public DataResponseDto<CommentInfoDto> updateComment(@RequestHeader("Authorization") String authorizationHeader,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentRequestDto commentRequestDto) {
        CommentInfoDto commentInfoDto = commentService.updateComment(authorization.extractUserIdFromHeader(authorizationHeader), commentId, commentRequestDto);
        return new DataResponseDto<>(Code.OK, "댓글 수정이 성공적으로 완료되었습니다.", commentInfoDto);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
