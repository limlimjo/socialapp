package com.sparta.socialapp.domain.comment.controller;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.comment.dto.CommentRequestDto;
import com.sparta.socialapp.domain.comment.dto.CommentResponseDto;
import com.sparta.socialapp.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> registerComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.registerComment(requestDto);
    }

    // 특정 게시글의 댓글 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentsByBoardId(boardId);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
