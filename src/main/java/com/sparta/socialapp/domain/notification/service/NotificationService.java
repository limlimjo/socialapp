package com.sparta.socialapp.domain.notification.service;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.board.repository.BoardRepository;
import com.sparta.socialapp.domain.comment.entity.Comment;
import com.sparta.socialapp.domain.comment.repository.CommentRepository;
import com.sparta.socialapp.domain.notification.dto.NotificationResponseDto;
import com.sparta.socialapp.domain.notification.entity.Notification;
import com.sparta.socialapp.domain.notification.repository.NotificationRepository;
import com.sparta.socialapp.domain.user.entity.User;
import com.sparta.socialapp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시글 댓글 알람 생성
    @Transactional
    public ResponseEntity<ApiResponse<NotificationResponseDto>> createCommentNotification(Long boardId, Long commentId, Long senderId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        User recipient = board.getUser();
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (recipient.getId().equals(sender.getId())) {
            return ApiResponse.success(null);
        }

        String message = String.format("%s님이 '%s' 게시글에 댓글을 남겼습니다: %s",
                    sender.getUsername(),
                    truncateText(board.getTitle(), 30),
                    truncateText(comment.getContent(), 50));

        Notification notification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .eventType(Notification.NotiEventType.BOARD_COMMENT_CREATED)
                .content(message)
                .board(board)
                .comment(comment)
                .isRead(false)
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        log.info("댓글 알람 생성이 완료되었습니다.: recipientId={}, senderId={}, boardId={}", recipient.getId(), sender.getId(), boardId);

        NotificationResponseDto responseDto = NotificationResponseDto.builder()
                .id(notification.getId())
                .senderUsername(notification.getSender().getUsername())
                .eventType(notification.getEventType().name())
                .content(notification.getContent())
                .boardId(notification.getBoard() != null ? notification.getBoard().getId() : null)
                .commentId(notification.getComment() != null ? notification.getComment().getId() : null)
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 게시글 좋아요 알람 생성
    @Transactional
    public ResponseEntity<ApiResponse<NotificationResponseDto>> createBoardLikeNotification(Long boardId, Long senderId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        User recipient = board.getUser();
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (recipient.getId().equals(sender.getId())) {
            return ApiResponse.success(null);
        }

        String message = String.format("%s님이 '%s' 게시글에 좋아요를 눌렀습니다.",
                sender.getUsername(),
                truncateText(board.getTitle(), 30));

        Notification notification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .eventType(Notification.NotiEventType.BOARD_LIKED)
                .content(message)
                .board(board)
                .isRead(false)
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        log.info("게시글 좋아요 알람 생성이 완료되었습니다.: recipientId={}, senderId={}, boardId={}", recipient.getId(), sender.getId(), boardId);

        NotificationResponseDto responseDto = NotificationResponseDto.builder()
                .id(notification.getId())
                .senderUsername(notification.getSender().getUsername())
                .eventType(notification.getEventType().name())
                .content(notification.getContent())
                .boardId(notification.getBoard() != null ? notification.getBoard().getId() : null)
                .commentId(notification.getComment() != null ? notification.getComment().getId() : null)
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 댓글 좋아요 알람 생성
    @Transactional
    public ResponseEntity<ApiResponse<NotificationResponseDto>> createCommentLikeNotification(Long commentId, Long senderId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        User recipient = comment.getUser();
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (recipient.getId().equals(sender.getId())) {
            return ApiResponse.success(null);
        }

        String message = String.format("%s님이 '%s' 댓글에 좋아요를 눌렀습니다.",
                sender.getUsername(),
                truncateText(comment.getContent(), 50));

        Notification notification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .eventType(Notification.NotiEventType.COMMENT_LIKED)
                .content(message)
                .comment(comment)
                .isRead(false)
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        log.info("댓글 좋아요 알람 생성이 완료되었습니다.: recipientId={}, senderId={}, commentId={}", recipient.getId(), sender.getId(), commentId);

        NotificationResponseDto responseDto = NotificationResponseDto.builder()
                .id(notification.getId())
                .senderUsername(notification.getSender().getUsername())
                .eventType(notification.getEventType().name())
                .content(notification.getContent())
                .boardId(notification.getBoard() != null ? notification.getBoard().getId() : null)
                .commentId(notification.getComment() != null ? notification.getComment().getId() : null)
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();

        return ApiResponse.success(responseDto);
    }

    // 자르기 유틸 메서드
    private String truncateText(String text, int maxLength) {

        if (text == null) {
            return "";
        }
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }
}
