package com.sparta.socialapp.domain.comment_like.service;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.comment.entity.Comment;
import com.sparta.socialapp.domain.comment.repository.CommentRepository;
import com.sparta.socialapp.domain.comment_like.entity.CommentLike;
import com.sparta.socialapp.domain.comment_like.repository.CommentLikeRepository;
import com.sparta.socialapp.domain.notification.service.NotificationService;
import com.sparta.socialapp.domain.user.entity.User;
import com.sparta.socialapp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    // 댓글 좋아요/좋아요 취소
    @Transactional
    public ResponseEntity<ApiResponse<String>> toggleCommentLike(Long commentId, Long userId) {
        Optional<CommentLike> commentLike = commentLikeRepository.findById(commentId);

        if (commentLike.isPresent()) {
            CommentLike like = commentLike.get();
           if("LIKED".equals(like.getLikeStatus())) {
               like.setLikeStatus("CANCELED");
               like.setUpdatedAt(LocalDateTime.now());
               commentLikeRepository.save(like);
               return ApiResponse.success("좋아요가 취소되었습니다.");
           } else {
               like.setLikeStatus("LIKED");
               like.setUpdatedAt(LocalDateTime.now());
               commentLikeRepository.save(like);
               // 알람 저장
               notificationService.createCommentLikeNotification(commentId, userId);
               return ApiResponse.success("좋아요가 추가되었습니다.");
           }

        } else {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당하는 유저가 없습니다."));

            CommentLike newLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .likeStatus("LIKED")
                    .build();

            commentLikeRepository.save(newLike);
            // 알람 저장
            notificationService.createCommentLikeNotification(commentId, userId);
            return ApiResponse.success("좋아요가 추가되었습니다.");
        }
    }

    // 댓글 좋아요 갯수
    public Long getCommentLikeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}
