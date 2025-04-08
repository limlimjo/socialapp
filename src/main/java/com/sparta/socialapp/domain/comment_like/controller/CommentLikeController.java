package com.sparta.socialapp.domain.comment_like.controller;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.comment_like.service.CommentLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글 ID와 유저 ID에 따른 좋아요/좋아요 취소
    @PostMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<ApiResponse<String>> toggleCommentLike(@PathVariable Long commentId, @PathVariable Long userId, HttpServletRequest request) {
        return commentLikeService.toggleCommentLike(commentId, userId, request);
    }

    // 댓글 ID에 따른 좋아요 갯수
    @GetMapping("/comment/{commentId}")
    public Long getCommentLikeCount(@PathVariable Long commentId) {
        return commentLikeService.getCommentLikeCount(commentId);
    }

}
