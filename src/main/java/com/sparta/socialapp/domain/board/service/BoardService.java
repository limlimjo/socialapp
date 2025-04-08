package com.sparta.socialapp.domain.board.service;

import com.sparta.socialapp.common.api.ApiResponse;

import com.sparta.socialapp.common.logger.service.EventLogService;
import com.sparta.socialapp.domain.board.dto.BoardRequestDto;
import com.sparta.socialapp.domain.board.dto.BoardResponseDto;
import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.board.repository.BoardRepository;
import com.sparta.socialapp.domain.user.entity.User;
import com.sparta.socialapp.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 적용 (성능 최적화 가능)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final EventLogService eventLogService;

    // 게시글 작성
    @Transactional
    public ResponseEntity<ApiResponse<BoardResponseDto>> registerBoard(BoardRequestDto requestDto) {

        // 인증된 사용자 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        // 사용자 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Board board = Board.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        Board saveBoard = boardRepository.save(board);

        BoardResponseDto responseDto = BoardResponseDto.builder()
                .id(saveBoard.getId())
                .userId(saveBoard.getUser().getId())
                .title(saveBoard.getTitle())
                .content(saveBoard.getContent())
                .createdAt(saveBoard.getCreatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 게시글 전체 조회
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getAllBoards() {
        List<BoardResponseDto> boardList = boardRepository.findAll().stream()
                .map(board -> BoardResponseDto.builder()
                        .id(board.getId())
                        .userId(board.getUser().getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ApiResponse.success(boardList);
    }

    // 특정 게시글 조회
    public ResponseEntity<ApiResponse<BoardResponseDto>> getBoardById(Long id, HttpServletRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        BoardResponseDto responseDto = BoardResponseDto.builder()
                .id(board.getId())
                .userId(board.getUser().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();

        // click event log
        eventLogService.logClickBoardDetailPage(board, request);

        return ApiResponse.success(responseDto);
    }

    // 게시글 수정
    @Transactional
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(Long id, BoardRequestDto requestDto) {

        // 인증된 사용자 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getEmail().equals(email)) {
            throw new RuntimeException("게시글 수정 권한이 없습니다.");
        }

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());

        Board updateBoard = boardRepository.save(board);

        BoardResponseDto responseDto = BoardResponseDto.builder()
                .id(updateBoard.getId())
                .userId(updateBoard.getUser().getId())
                .title(updateBoard.getTitle())
                .content(updateBoard.getContent())
                .updatedAt(updateBoard.getUpdatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<ApiResponse<String>> deleteBoard(Long id) {

        // 인증된 사용자 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getEmail().equals(email)) {
            throw new RuntimeException("게시글 수정 권한이 없습니다.");
        }

        boardRepository.deleteById(id);

        return ApiResponse.success("게시글이 삭제되었습니다.");
    }
}
