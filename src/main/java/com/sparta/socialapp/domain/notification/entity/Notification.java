package com.sparta.socialapp.domain.notification.entity;

import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.comment.entity.Comment;
import com.sparta.socialapp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    User sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    NotiEventType eventType;

    @Column(nullable = false, length = 500)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    Comment comment;

    @Column(name = "is_read", nullable = false)
    Boolean isRead = false;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public enum NotiEventType {
        BOARD_COMMENT_CREATED,
        BOARD_LIKED,
        COMMENT_LIKED
    }
}
