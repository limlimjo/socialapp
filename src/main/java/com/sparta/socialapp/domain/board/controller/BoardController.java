package com.sparta.socialapp.domain.board.controller;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.common.logger.EventLogFactory;
import com.sparta.socialapp.domain.board.dto.BoardRequestDto;
import com.sparta.socialapp.domain.board.dto.BoardResponseDto;
import com.sparta.socialapp.domain.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponseDto>> registerBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.registerBoard(requestDto);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getAllBoards() {
        return boardService.getAllBoards();
    }

    // 특정 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> getBoardById(@PathVariable Long id, HttpServletRequest request) {

        return boardService.getBoardById(id, request);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }
}
