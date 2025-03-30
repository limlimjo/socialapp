package com.sparta.socialapp.domain.comment.service;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.board.dto.BoardResponseDto;
import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.board.repository.BoardRepository;
import com.sparta.socialapp.domain.comment.dto.CommentRequestDto;
import com.sparta.socialapp.domain.comment.dto.CommentResponseDto;
import com.sparta.socialapp.domain.comment.entity.Comment;
import com.sparta.socialapp.domain.comment.repository.CommentRepository;
import com.sparta.socialapp.domain.notification.service.NotificationService;
import com.sparta.socialapp.domain.user.entity.User;
import com.sparta.socialapp.domain.user.repository.UserRepository;
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
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    // 댓글 등록
    @Transactional
    public ResponseEntity<ApiResponse<CommentResponseDto>> registerComment(CommentRequestDto requestDto) {

        // 인증된 사용자 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        // 사용자 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 게시글 확인
        Board board = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .user(user)
                .board(board)
                .content(requestDto.getContent())
                .build();

        Comment saveComment = commentRepository.save(comment);

        // 알람 저장
        notificationService.createCommentNotification(board.getId(), saveComment.getId(), user.getId());

        CommentResponseDto responseDto = CommentResponseDto.builder()
                .id(saveComment.getId())
                .boardId(saveComment.getBoard().getId())
                .userId(saveComment.getUser().getId())
                .content(saveComment.getContent())
                .createdAt(saveComment.getCreatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 특정 게시글의 댓글 조회
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getCommentsByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        List<CommentResponseDto> commentList = commentRepository.findByBoardId(boardId).stream()
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .userId(comment.getUser().getId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ApiResponse.success(commentList);
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(Long id, CommentRequestDto requestDto) {

        // 인증된 사용자 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글 찾을 수 없습니다."));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("댓글 수정 권한이 없습니다.");
        }

        comment.setContent(requestDto.getContent());

        Comment updateComment = commentRepository.save(comment);

        CommentResponseDto responseDto = CommentResponseDto.builder()
                .id(updateComment.getId())
                .userId(updateComment.getUser().getId())
                .content(updateComment.getContent())
                .updatedAt(updateComment.getUpdatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<ApiResponse<String>> deleteComment(Long id) {

        // 인증된 사용자 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글 찾을 수 없습니다."));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("댓글 수정 권한이 없습니다.");
        }

        commentRepository.deleteById(id);
        return ApiResponse.success("댓글이 삭제되었습니다.");
    }



}
