package com.sparta.socialapp.domain.board_like.entity;

import com.sparta.socialapp.domain.board.entity.Board;
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
@Table(name = "board_like", uniqueConstraints = {@UniqueConstraint(columnNames = {"board_id", "user_id"})})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "like_status", nullable = false, columnDefinition = "ENUM('LIKED', 'CANCELED') DEFAULT 'LIKED'")
    String likeStatus = "LIKED";

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
