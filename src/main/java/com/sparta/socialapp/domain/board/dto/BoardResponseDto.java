package com.sparta.socialapp.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDto {
    Long id;
    Long userId;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
