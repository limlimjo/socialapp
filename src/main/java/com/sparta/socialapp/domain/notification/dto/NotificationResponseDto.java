package com.sparta.socialapp.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {
    Long id;
    String senderUsername;
    String eventType;
    String content;
    Long boardId;
    Long commentId;
    Boolean isRead;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
