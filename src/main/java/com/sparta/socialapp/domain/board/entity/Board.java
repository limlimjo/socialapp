package com.sparta.socialapp.domain.board.entity;

import com.sparta.socialapp.domain.comment.entity.Comment;
import com.sparta.socialapp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 다대일 관계 설정 (여러 개의 Board 엔티티는 하나의 User 엔티티 가짐)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false, length = 255)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    // 일대다 관계 설정 (하나의 Board 엔티티는 여러 개의 Comment 엔티티 가짐)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;
}
