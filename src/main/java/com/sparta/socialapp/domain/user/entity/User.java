package com.sparta.socialapp.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

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

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, length = 50)
    String username;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;
}
