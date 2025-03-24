package com.sparta.socialapp.domain.user.entity;

import com.sparta.socialapp.domain.board.entity.Board;
import com.sparta.socialapp.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 100, unique = true)
    String email;

    @Column(nullable = false, length = 50)
    String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    String passwordHash;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    // 일대다 관계 설정 (하나의 User 엔티티는 여러 개의 Board 엔티티 가짐)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Board> boards;

    // 일대다 관계 설정 (하나의 User 엔티티는 여러 개의 Comment 엔티티 가짐)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;
}
