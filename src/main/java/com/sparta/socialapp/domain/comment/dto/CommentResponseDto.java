package com.sparta.socialapp.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    Long id;
    Long boardId;
    Long userId;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
