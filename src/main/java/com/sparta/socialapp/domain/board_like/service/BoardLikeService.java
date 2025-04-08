package com.sparta.socialapp.domain.board_like.service;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.common.logger.service.EventLogService;
import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.board.repository.BoardRepository;
import com.sparta.socialapp.domain.board_like.entity.BoardLike;
import com.sparta.socialapp.domain.board_like.repository.BoardLikeRepository;
import com.sparta.socialapp.domain.notification.service.NotificationService;
import com.sparta.socialapp.domain.user.entity.User;
import com.sparta.socialapp.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final EventLogService eventLogService;

    // 게시글 좋아요/좋아요 취소
    @Transactional
    public ResponseEntity<ApiResponse<String>> toggleBoardLike(Long boardId, Long userId, HttpServletRequest request) {
        Optional<BoardLike> boardLike = boardLikeRepository.findById(boardId);

        if (boardLike.isPresent()) {
            BoardLike like = boardLike.get();
            if("LIKED".equals(like.getLikeStatus())){
                like.setLikeStatus("CANCELED");
                like.setUpdatedAt(LocalDateTime.now());
                boardLikeRepository.save(like);
                return ApiResponse.success("좋아요가 취소되었습니다.");
            } else {
                like.setLikeStatus("LIKED");
                like.setUpdatedAt(LocalDateTime.now());
                boardLikeRepository.save(like);

                // wish event log
                eventLogService.logWishBoardLike(boardId, request);

                // 알람 저장
                notificationService.createBoardLikeNotification(boardId, userId);
                return ApiResponse.success("좋아요가 추가되었습니다.");
            }

        } else {
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당하는 유저가 없습니다."));

            BoardLike newLike = BoardLike.builder()
                    .board(board)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .likeStatus("LIKED")
                    .build();

            boardLikeRepository.save(newLike);

            // wish event log
            eventLogService.logWishBoardLike(boardId, request);

            // 알람 저장
            notificationService.createBoardLikeNotification(boardId, userId);
            return ApiResponse.success("좋아요가 추가되었습니다.");
        }
    }

    // 게시글 좋아요 갯수
    public Long getBoardLikeCount(Long boardId) {
        return boardLikeRepository.countByBoardId(boardId);
    }
}
