package com.sparta.socialapp.domain.board_like.controller;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.board_like.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    // 게시글 ID와 유저 ID에 따른 좋아요/좋아요 취소
    @PostMapping("/board/{boardId}/user/{userId}")
    public ResponseEntity<ApiResponse<String>> toggleBoardLike(@PathVariable Long boardId, @PathVariable Long userId) {
        return boardLikeService.toggleBoardLike(boardId, userId);
    }

    // 게시글 ID에 따른 좋아요 갯수
    @GetMapping("/board/{boardId}")
    public Long getBoardLikeCount(@PathVariable Long boardId) {
        return boardLikeService.getBoardLikeCount(boardId);
    }
}
